<h2 align="center"> 
	SeiTchiz - An Instagram clone 🚀 DONE ✅
</h2>

## Grupo 46

- Ana Albuquerque 53512

- Gonçalo Antunes 52831

- Tiago Cabrita 52741

## Como compilar o projeto?

Abra o terminal dentro da pasta 'SegC-grupo46-proj1-2' para executar os próximos comandos.

### RUN
Para correr o programa com os ficheiros de permissões corretamente é necessario e que este se encontre localizado na home do utilizador: $HOME/SegC-grupo46-proj1-2/Fase1-Parte2/SeiTchiz/

### Servidor:

Navege para a pasta server:
cd server/libs/

Coloque este comando no terminal:
java -jar Server.jar [Port] <keystore> <keystore-password>

### Cliente:

Navege para a pasta client:
cd client/libs/

Coloque este comando no terminal:
java -jar Client.jar <IP>[:Port] <username> <truststore> <keystore> <keystore-password> <localUserID>

Nota: Os campos contidos dentro de [ ] podem não ser colocados.

### Keystores:
A keystore presente no servidor (server/keystore.server) utiliza a palavra-passe: ourserverpass. Qualquer uma das keystores do cliente (client/keystore.client) utilizam a mesma palavra-passe: ourclientpass.

#### Exemplo de utilização:
##### Servidor:  
   `cd Fase1-Parte2/server/libs`  
   `java -jar Server.jar 45678 ../keystore.server ourserverpass`  
##### Cliente:    
   `java -jar Client.jar 127.0.0.1:45678 ../truststore.client ../keystore.ana ourclientpass ana`

## Limitações:

Na parte 1 fase 2, não conseguimos colocar o cliente e o servidor a correr numa sandbox.

### Cliente:

- Por omissão a conexão é no porto 45678 (porto onde escuta o servidor).
- Para postar uma foto é colocá-la na pasta UserFiles que está na pasta ``` client/ ```.

O cliente apenas reconhece os seguintes comandos/atalhos:


|       Comandos              |          Atalhos       |
|-----------------------------|:----------------------:|
|			follow [userID]					| f [userID]             |
| unfollow [userID]	          | u [userID] 	        	 |
| viewfollowers								| v                      |
| post [photo]								|	p [photo]              |
| wall [nPhotos]							| w [nPhotos]            |
| like [photoID]							| l [photoID]	           |
| newgroup [groupID]					| n [groupID]            |
| addu/a [userID]	[groupID]		| a [userID] [groupID]   |
| removeu [userID] [groupID]	| r [userID] [groupID]   |
| ginfo [groupID]							| g	                     |
| msg [groupID] [msg]					| m [groupID] [msg]      |
| collect [groupID]						| c [groupID]            |
| history [groupID]						| h [groupID]	           |
| menu												| --                     |
| quit												|	q		          	       |

### Servidor:
- Dentro da pasta ``` Data/ ``` estão as pastas com as diversas informações relativas ao group; post; follow.
- Não é possível eliminar posts; grupos e mensagens.

