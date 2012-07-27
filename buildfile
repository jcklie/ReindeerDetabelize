require 'fileutils'

repositories.remote << 'http://mvnrepository.com' << 'http://www.ibiblio.org/maven2' << 'http://repo1.maven.org/maven2'

NLP_V = '1.3.2'

NLP = transitive('edu.stanford.nlp:stanford-corenlp:jar:1.3.2')
LOGGING = 'commons-logging:commons-logging:jar:1.1.1'
JCOMMANDER = 'com.beust:jcommander:jar:1.27'
LOG4J = transitive('log4j:log4j:jar:1.2.16')
NLP_MODELS = "nlp-models:nlp-models:jar:#{NLP_V}"
POI = 'org.apache.poi:poi:jar:3.8'


URL = struct(
  :m  => "http://mirrors.ibiblio.org/pub/mirrors/maven2/edu/stanford/nlp/stanford-corenlp/#{NLP_V}/stanford-corenlp-#{NLP_V}-models.jar" 
)

download(artifact("nlp-models:nlp-models:jar:#{NLP_V}")=>URL.m)

define 'rentier-detabelize' do
	project.version = '0.0.1'
	compile.with NLP, LOG4J, LOGGING, JCOMMANDER, POI
	package :jar
end
