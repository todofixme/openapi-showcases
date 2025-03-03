## Start infrastructure (incl. Keycloak)
```shell
docker compose up 
```

# Set up Keycloak
When running for the first time, real ShelPatrolApp with client shelf-patrol-client, users "admin" and "user" are created and roles are granted:
* user: author:read, literature:read 
* admin: author:read, author:write, literature:read, literature:write 

## Set up Keycloak manually
* Access Keycloak: http://localhost:8080/auth
* Create realm (ShelPatrolApp)
* Create client (shelf-patrol-client)
  * Web origins: +
  * Create role for client todo-app-client (user, admin)
  * Create realm roles (app_user, app_admin)
  * Select role, Action: Add associated role, Filter by clients, <client-name> / <role-name>
  * Create new user, Credentials, Set password (Temporary off), Role mapping, Assign role
