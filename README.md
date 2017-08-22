# proof-of-concept-switch-ws-or-api
An Eclipse workspace with a complete example to switch easily from WS to API java call and vice-versa

Ce projet est une démonstration d'une technique de switch très simple entre le mode d'appel par API et le mode d'appel WS. Il fournit un exmple de structure de projets volontairement éclatée bâtis autour de Maven.

## Présentation du worskpace
Le workspace est composé de 6 projects maven et correspond à 1 seule application web :

|Projet|Fonction|
|---   |---     |
|[parentpom](./parentpom)   |Ce projet contient le pom parent pour les dépendances et la configuration Maven commune. Les projets common, services-api et services-ws pourraient en être des modules.|
|[common](./common)      |Le projet common contient la clé de voûte du swith : Les interfaces de services, les VOs, et les exceptions commune, généralement ServiceException et TechniqueException|
|[services-api](./services-api)|Ce projet est la couche service de l'application. Il implémente les interfaces de 'common' et utilise les VO définie dans common. Service API ne dépend que de common.      |
|[services-ws](./services-ws) |Ce projet est le client dont a besoin un projet client pour accèder au service web. La structure de ce projet, package compris, doit être identique à services-api. Il ne faut jamais importer services-api et services-ws en même temps.|
|[webmodule](./webmoduled)   |Ce projet est le serveur web client. C'est lui qui référence services-api *OU* services-ws|
|[webservices](./webservices) |Ce projet est le serveur de Web services. Il dépend de services-api.        |

![Dependencies of projects](./resource/dependencies_project.png)

Le projet webmodule ne dépend QUE de services-api *OU* de services-ws, jamais les 2. Dans le cas où une partie est exposée et l'autre non, il faut éclater services-xxx en autant de projets que groupes de services.

## Raisons
Un appel WS est typiquement environ 1000 fois plus lent qu'un appel par API java. C'est donc principalement la performance qui nous pousse à chercher à basculer de façon _transparente_ d'un protocole à l'autre. 

La bascule doit être aussi simple que de modifier une ligne dans un fichier pom.xml :
```xml
		<dependency>
			<groupId>dcn.ovh</groupId>
<!-- 			<artifactId>services-api</artifactId> -->
			<artifactId>services-ws</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>
````
La bascule se fait simplement en commentant service-ws et en décommentant services-api.

## Principe


## Mise en oeuvre