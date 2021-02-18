FROM openjdk:11

RUN mkdir /SpaceInvaders

WORKDIR /SpaceInvaders

RUN apt-get update \
	&& apt-get install curl \
	&& apt-get install -y libxrender1 libxtst6 libxi6

RUN curl -L -H "Accept: application/vnd.github.v3+json" -H "Authorization: {token}" https://api.github.com/repos/Marcolinoino/ProjectSpaceInvaders/actions/artifacts/41691563/zip --output SpaceInvaders.zip

RUN unzip SpaceInvaders.zip

CMD ["java", "-jar", "SpaceInvaders-1.0.jar"]
