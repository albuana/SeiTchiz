<h2 align="center"> 
	🚧  SeiTchiz - An Instagram clone 🚀 Em construção...  🚧
</h2>

## Grupo 46

- Ana Albuquerque 53512

- Gonçalo Antunes 52831

- Tiago Cabrita 52741

## Como executar o projeto?

Abra o terminal dentro da pasta 'SeiTchiz/Fase1-Parte1' para executar os próximos comandos.

### RUN
Para correr o programa com os ficheiros de permissões corretamente é necessario e que este se encontre localizado na home do utilizador: $HOME/SeiTchiz/Fase1-Parte1

### Servidor:

Navege para a pasta server:
cd server/

Coloque este comando no terminal:
java -cp bin -Djava.security.manager -Djava.security.policy=server.policy server.SeiTchizServer <Port>

### Cliente:

Navege para a pasta client:
cd client/

Coloque este comando no terminal:
java -cp bin -Djava.security.manager -Djava.security.policy=client.policy client.SeiTchiz <IP>[:Port] <username> [password]

Nota: Os campos contidos dentro de [ ] podem não ser colocados.

## Limitações:

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

