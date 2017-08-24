# proof-of-concept-switch-ws-or-api
A java Eclipse workspace with a complete example to switch easily from WS to API java call and vice-versa

Ce projet est une démonstration d'une technique de switch très simple entre le mode d'appel par API et le mode d'appel WS avec la librairie apache-cxf **sans utiliser spring**. Il fournit un exemple de structure volontairement éclatée de modules bâtis autour de Maven.

Notre équipe en a eu besoin récemment, je partage donc le résultat de nos recherches :

## Présentation du worskpace
Le workspace est composé de 6 projects maven et correspond à 1 seule application web :

|Projet|Fonction|
|---   |---     |
|[parentpom](./parentpom)   |Ce projet contient le pom parent pour les dépendances et la configuration Maven commune. Les projets common, services-api et services-ws pourraient en être des modules. Pour plus de simplicité dans le cadre de ce POC, ces projets sont dans des projets Eclipse indépendants.|
|[common](./common)      |Le projet common contient la clé de voûte du swith : Les interfaces de services et la déclation des exceptions supportés, les VOs, et les types d'exception, généralement ServiceException et TechnicalException|
|[services-api](./services-api)|Ce projet est la couche service de l'application. Il implémente les interfaces du projet [common](./common) et en consomme les VO. [services-api](./services-api) ne dépend **que de** [common](./common).      |
|[services-ws](./services-ws) |Ce projet est le client CXF dont a besoin le projet [webmodule](./webmodule) pour accèder aux webservices. La structure de ce projet, noms de classes et packages compris, doit être identique à [services-api](./services-api). Il ne faut jamais importer [services-api](./services-api) et [services-ws](./services-ws) en même temps.|
|[webmodule](./webmodule)   |Ce projet est le serveur web. C'est lui qui référence [services-api](./services-api) *OU* [services-ws](./services-ws)|
|[webservices](./webservices) |Ce projet est le serveur de Web services basées sur CXF. Il dépend de [services-api](./services-api).        |

![Dependencies of projects](./resources/dependencies_project.png)

Le projet [webmodule](./webmodule) n'a besoin de dépendre QUE de [services-api](./services-api) *OU* [services-ws](./services-ws), jamais les 2. Dans le cas où seulement une partie des services est exposée, il faut éclater services-xxx en autant de projets que de groupes de services.

## Raisons
Un appel WS est typiquement environ 1000 fois plus lent _en temps d'accès_ qu'un appel par API java. C'est donc principalement la performance qui nous pousse à chercher à basculer de façon _transparente_ d'un protocole à l'autre. 

La bascule doit être aussi simple que de modifier une ligne dans un fichier pom.xml :

```xml
		<dependency>
			<groupId>dcn.ovh</groupId>
<!-- 			<artifactId>services-api</artifactId> -->
			<artifactId>services-ws</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>
```

La bascule se fait simplement en commentant service-ws et en décommentant services-api. (en pratique, il faut aussi modifier le web.xml et/ou le context.xml du serveur web pour les datasources par exemple.)

En raison des difficultés que notre équipe a eu avec l'IoC, nous préférons désormais le code le plus ouvert possible (<> du code boite noire), 'sans magie à la spring'. Le projet fait usage de CXF exclusivement par programmation par exemple.

Enfin, pour gagner en temps de développement, nous souhaitons faire travailler nos développeurs en mode java API en développement, puis d'activer le mode WS pour les tests d'intégration et si nécessaire, en production.

## Principe
A partir du projet 'webmodule', un appel à un service (ou webservice) java se fait comme suit :

```java
		try {
			Coordination.students.changeName(student);
			out.print("<br /> NO EXCEPTION");
		} catch (ServiceException se) {
			out.print("<br />Exception fonctionnelle : " + se.getMessage();
		} catch (TechnicaException te) {
			out.print("<br />Exception tehnique : " + te.getMessage();
		}
```

Les exceptions remontées doivent se comporter exactement de la même façon en mode java API ou WS. La seule différence est qu'une RuntimeException non déclarée dans l'interface du projet [common](./common) est encapsulée dans une RuntimeException 'SOAPFault' quand le mode WS est activée.

## Mise en oeuvre
![Dependencies of projects](./resources/class_hierarchy.png)

Le découpage en module MAVEN décrit plus haut permet de tirer les dépendances nécessaires sans chevauchement. Attention toutefois, 'ServiceAPI' et 'ServiceWS' sont dans des modules maven différents, mais ne doivent jamais être déclarés ensemble dans un même pom.xml. En effet, ces 2 modules ont des noms de packages et de classe identiques.  

N'hésitez pas à adapter ce code, si tant est que vous en ayez besoin.