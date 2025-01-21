# Configurator
## Description
There is an CNCF main configuration file which is placed at https://github.com/cncf/landscape repository.
In case you want to extends/modify such configuration with your own configurations - you can fork cncf/landscape
repository, add your new *.yml files in same format and use this tool to merge all this configurations
into single common YAML file which can be rendered with https://github.com/cncf/landscape2 application.

As a side effect (somewhere positive, somewhere negative) the model of landscape.yml format is hardcoded
in this configurator application, so all unknown model items will be ignored. This brings stability and backward compatibility
by require configurator application to support new items.

## How to builb
mvn clean package

## How to use
java -jar configurator.jar