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
- Para postar uma foto é necessário colocá-la na pasta UserFiles que está na pasta ``` client/ ```. Tem de verificar se está a correr pelo eclipse. Se não estiver, terá de colocar na pasta UserFiles que está na pasta libs (onde está o executável).

O cliente apenas reconhece os seguintes comandos/atalhos:


|       Comandos              |          Atalhos       |
|-----------------------------|:----------------------:|
|			follow [userID]					| f [userID]             |
| unfollow [userID]	          | u [userID] 	        	 |
| viewfollowers								| v                      |
| post [photo]	*							|	p [photo]              |
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

Para o cliente se autenticar com sucesso, é necessário que tenha na sua truststore o certificado do servidor, assim como o seu par de chaves assimétricas RSA na sua keystore.

Para adicionar um utilizador a um grupo, é necessário que a chave pública do mesmo (no formato <username>.cer) esteja presente no diretório server/Data/publicKeys.

Ao fazer post, as fotos serão colocadas num diretório server/Data/posts/(nome do uti que fez post)/ .

Ao fazer msg, as mensagens serão colocadas num diretório server/Data/group/(nome do grupo)/collect/<nome de utilizador no grupo>collect.txt.
	
Ao fazer collect, as mensagens serão colocadas num diretório server/Data/group/(nome do grupo)/collect/<nome de utilizador no grupo>history.txt. e removidas do collect respectivo.
	

### Cliente:
As respectivas keystores de cada cliente encontram-se na pasta Fase1-Parte2/SeiTchiz/client

Para que o cliente consiga fazer post utilizando o jar é preciso que se encontre uma pasta Userfiles/ com as fotos na localização do jar.


