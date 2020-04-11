# Builds image for the REST service

FROM openjdk:11

COPY . /usr/src/gameoflife

RUN cd /usr/src/gameoflife/ \
  && ./gradlew :presentation-rest:installDist --no-daemon

EXPOSE 4567

CMD /usr/src/gameoflife/presentation-rest/build/install/gameoflife-rest/bin/gameoflife-rest