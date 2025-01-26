FROM maven:3.9.9-amazoncorretto-11-al2023 AS builder

# compile configurator application
COPY addons/configurator /tmp/build

WORKDIR /tmp/build

RUN /usr/bin/mvn clean package
RUN mkdir -p /tmp/cncf_config
RUN cp /tmp/build/target/configurator.jar /tmp/cncf_config/configurator.jar

# merge extra configurations into main landscape.yml
COPY addons/extra_yamls /tmp/cncf_config/extra_yamls
COPY landscape.yml /tmp/cncf_config

WORKDIR /tmp/cncf_config

RUN java -jar configurator.jar --primary-file /tmp/cncf_config/landscape.yml --path-to-scan ./extra_yamls --output-file-name result.yml

# build landscape application
FROM public.ecr.aws/g6m3a0y9/landscape2:latest AS prod
EXPOSE 80

USER landscape2
WORKDIR /home/landscape2

RUN landscape2 new --output-dir my-landscape
COPY addons/extra_yamls/settings_override.yml /home/landscape2/my-landscape/settings.yml
COPY --from=builder /tmp/cncf_config/result.yml /home/landscape2/my-landscape/data.yml
COPY hosted_logos /home/landscape2/my-landscape/logos

WORKDIR /home/landscape2/my-landscape
RUN landscape2 build --data-file data.yml --settings-file settings.yml --guide-file guide.yml --logos-path /home/landscape2/logos --output-dir build

## todo make new clear stage
WORKDIR /home/landscape2/my-landscape
ENTRYPOINT ["landscape2", "serve", "--addr", "0.0.0.0:80", "--landscape-dir", "build"]
