# Java Maven Sample Application

See [prerequisites](https://paketo.io/docs/howto/java/#prerequisites) of this sample.

## Building with pack

```bash
pack build applications/maven
```

Alternatively, if you want to attach a Maven `settings.xml` file to pass additional configuration to Maven.

```bash
pack build applications/maven --env BP_JVM_VERSION=17 --volume $(pwd)/bindings:/platform/bindings
```

The command above will use the sample `settings.xml` file from this repo. It may be more useful to copy your local `settings.xml` first.

```bash
cp ~/.m2/settings.xml java/maven/bindings/maven/settings.xml
pack build applications/maven --volume $(pwd)/bindings:/platform/bindings
```

## Building with shipwright

Use the buildpacks ClusterStrategy from redhatbuildpacks repo https://github.com/redhat-buildpacks/testing.git

```bash
oc create -f shipwright/clusterbuildstrategy.yaml
```

Using a Maven `settings.xml`, create a mvn-bindings secret
```bash
oc create secret generic mvn-bindings --from-file=settings.xml=./cnb-sample-java/bindings/maven/settings.xml
```

Generate a docker-registry secret that will be used by the serviceAccount of the build's pod to access the container registry

```bash
oc create secret docker-registry registry-creds --docker-server=https://index.docker.io/v1/  --docker-username=xxx --docker-password='xxx'
```

Create a serviceAccount that the platform will use to perform the build and able to be authenticated using the
secret's credentials with the registry

```bash
oc create -f k8s/shipwright/sa.yml
```

Create the shipwright build 
```bash
oc create -f shipwright/build.yml
```

Check the shipwright build 
```bash
oc get builds.shipwright.io

NAME                   REGISTERED   REASON      BUILDSTRATEGYKIND      BUILDSTRATEGYNAME   CREATIONTIME
buildpack-mvn-build    True         Succeeded   ClusterBuildStrategy   buildpacks          7s
```

Create the shipwright buildRun
```bash
oc create -f shipwright/buildrun.yml
```

Check the shipwright buildRun
```bash
oc get buildruns.shipwright.io

NAME                   REGISTERED   REASON      BUILDSTRATEGYKIND      BUILDSTRATEGYNAME   CREATIONTIME
buildpack-mvn-build    True         Succeeded   ClusterBuildStrategy   buildpacks          7s
```


## Running

```bash
docker run --rm --tty --publish 8080:8080 applications/maven
```

## Viewing

```bash
curl -s http://localhost:8080/actuator/health | jq .
```
