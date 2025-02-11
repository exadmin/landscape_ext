#!/bin/bash

########################################################################################################################
# Step1: Ensure JAVA is installed                                                                                      #
########################################################################################################################
if ! [ -x "$(command -v java)" ]; then
  echo 'Error: java is not installed. Try "sudo apt install openjdk-21-jre-headless"' >&2
  exit 1
fi

if ! [ -x "$(command -v mvn)" ]; then
  echo 'Error: Apache Maven is not installed.' >&2
  exit 1
fi

########################################################################################################################
# Step2: Clean up "out" folder to prepare application in                                                               #
########################################################################################################################
rm -rf ./out/
mkdir -p ./out/

########################################################################################################################
# Step3: Compile configurator java-application using maven                                                             #
########################################################################################################################
cd ./addons/configurator
mvn clean install package assembly:single

########################################################################################################################
# Step4: Copy built artifact into "out" folder and clean-up maven output dir                                           #
########################################################################################################################
cp ./target/configurator.jar ./../../out/configurator.jar
mvn clean

########################################################################################################################
# Step 5: Run configurator application to merge base landscape.yml with all *.yml in the "landscape-conf" folder       #
########################################################################################################################
cd ./../../out/
java -jar configurator.jar --primary-file ./../landscape.yml --path-to-scan ./../addons/landscape2/landscape-conf --output-file-name result.yml

########################################################################################################################
# Step 6: Ensure "landscape2" application is installed                                                                 #
########################################################################################################################
if ! [ -x "$(command -v landscape2)" ]; then
  echo 'Error: landscape2 is not installed. See installation notes at https://github.com/cncf/landscape2' >&2
  exit 1
fi

########################################################################################################################
# Step 7: Prepare new landscape instance from scratch but use created configuration and updated settings file          #
########################################################################################################################
rm -rf ./build
landscape2 new --output-dir mylandscape-app
rm ./mylandscape-app/data.yml
rm ./mylandscape-app/settings.yml
cp  ./result.yml ./mylandscape-app/data.yml
cp ./../addons/landscape2/app-conf/settings_override.yml ./mylandscape-app/settings.yml
cp  -rv ./../hosted_logos/* ./mylandscape-app/logos
cd mylandscape-app


landscape2 build --data-file data.yml --settings-file settings.yml --guide-file guide.yml --logos-path logos --output-dir build

landscape2 serve --landscape-dir build