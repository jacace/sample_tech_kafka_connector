Sample connector based on teh tutorial below:
https://opencredo.com/blogs/kafka-connect-source-connectors-a-detailed-guide-to-connecting-to-what-you-love/

# Building with maven

```
mvn archetype:generate -DarchetypeGroupId=io.confluent.maven -DarchetypeArtifactId=kafka-connect-quickstart -DarchetypeVersion=0.10.0.0 \
-Dpackage=com.examples \
-DgroupId=com.examples \
-DartifactId=sample_tech_kafka_connector \
-Dversion=1.0-SNAPSHOT
```

# Running in development

```
mvn clean package
export CLASSPATH="$(find target/ -type f -name '*.jar'| grep '\-package' | tr '\n' ':')"
$CONFLUENT_HOME/bin/connect-standalone $CONFLUENT_HOME/etc/schema-registry/connect-avro-standalone.properties config/MySourceConnector.properties
```
