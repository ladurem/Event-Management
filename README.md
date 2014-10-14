
##Architecture
Dans notre projet, nous avons sépare chaque type d’événement en associant une classe a chacun d’eux.
Notre projet comporte un seul package(cli ), qui contient toutes les classes
du programme. On appelle la classe mère PM.java, qui redirigera (en
fonction des arguments) la classe `a exécuter.
Détail et utilité de chaque fichier :
• Client.java : Classe qui permet d’implémenter la structure pour un
client.
• Concert.java : Classe qui permet d’implémenter la structure pour
un concert.
• Enchevalement : Classe qui permet en fonction des arguments
transmis, d’ajouter au fichier XML des nouveaux entonnements.
• Hockey.java : Classe qui permet d’implémenter la structure pour
un match de hockey.
• Importer.java : Classe qui permet de parcourir le fichier CSV donne
en argument, et de créer le fichier XML, dont le nom est également
donne en argument.
• Manifestation.java: Classe qui permet l’ajout de tout type de man-
ifestation. En fonction du deuxième argument, cela permet de
choisir la bonne classe en fonction du type d’ev`enement.
1• Patinage.java : Classe qui permet d’implémenter la structure pour
un ev`enement de type Patinage.
• PM.java : Classe mre qui permet de selectionner la classe `a appli-
quer en fonction des arguments.
• Rapporter.java : Classe qui permet de parcourir le fichier XML
donne en argument, et de generer un rapport en HTML, plus lis-
ible, pour afficher les donnees du fichiers XML et aussi quelques
statistiques.
• Resa.java : Classe qui permet d’implementer la structure pour une
reservation.
• Reserver.java : Classe qui permet de gerer les reservations. Elle
prends en entree le CSV ainsi que le fichier XML.

#Gestion des Erreurs :
Pour la gestions des erreurs dans les fichiers reservations et ajout
des evenements, nous avons opte pour une recherche des erreurs
de mani`ere recursive. On parcourt le fichier XML `a la recherche
de l’existence d’un ou plusieurs doublons. D`es qu’un tel cas est
detectee, on arrˆete la recherche, puis on ajoute le message d’erreur
dans une list. Avant l’ajout dans le XML on verifie le nombre
d’erreur.
#Difficultes rencontrees :
La comprehension du sujet fut difficile, car il y avait de nombreuses
classes a implementer. Nous avons du realiser une architecture pour
coder le projet. Nous avons eprouve, de grandes difficultes sur la
gestions des erreurs. En effet, nous avons rencontre des probl`emes
concernant la verification des erreurs. De plus l’implementation
des dates, qui nous permettait de gerer egalement les erreurs nous a
demande beaucoup de temps. Finalement, nous utilisons un format
simple du type DD/MM/YY.
Toutefois, nous avons ete ravis de pouvoir apprehender les bases
de ce langage tres utilise aujourd’hui. De plus un point positif est
que nous avons tres correctement gere le partage en SVN de notre
travail de groupe.
