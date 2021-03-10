# Role based access control system (RBAC) & JWT Auth
## Implented in Spring boot


This is a sample spring boot application implementing . The 

-  JWT Authentication. 
- RBAC (Role based access control)
- File Uploader
- File Serving
- Some extra models and controllers that simulate an e-commerce admin platoform (ex Products , Shops) (Not Finished yet)

## Roles
The supported roles are:
- User
- Moderator
- Administrator


## Role Authorization

To make a route available only for specific role you can use the following.
### User
```sh
	@PreAuthorize("hasRole('USER')")
```
### Moderator
```sh
	@PreAuthorize("hasRole('MODERATOR')")
```
### Administrator
```sh
	@PreAuthorize("hasRole('ADMIN')")
```

### More than one role
```sh
	@PreAuthorize("hasRole('role1') || hasRole('role2')")
```


## API

| Endpoint | Method |Usage
| ------ | ------ |----------------
| api/auth/signin | POST | User log in
| api/auth/signup | POST | User register
| api/users |GET | Get all Users
| api/users/{id} | PUT | Update user with {id}
| api/users/{id} | DELETE | Delete user with {id}
| api/files | GET | Get all files in server
| api/download/{filename:.+} | GET | Download specific file from server
| api/upload-file | POST | Upload a Multipart file
| api/products | GET | Get all products
| api/products/{id} | GET | Get product with {id}
| api/porducts | POST | Create new product
| api/products/{id} | PUT | Update product with {id}
| api/shops/{shopId}/products | GET | Get products of shop with {id}
| api/shops | GET | Get shops
| api/shops/{shopId} | GET | Get shop with {shopId}
| api/shops/{shopId} | PUT | Update shop with {shopId}
| api/shops | POST | Create new shop
| api/shops/{shopId} | DELETE | Delete shop with {shopId}


## Configure
The project is currently test on SQL database with a database with a pre-built table with the roles and an admin user, then you can add more via the api.
### roles
| id | name |
| ------ | ------ |
| 1 | ROLE_USER |
| 2 | ROLE_MODERATOR |
| 3 | ROLE_ADMIN |
> Note: You can add your configuration (Database, jwt Secret Key , expiration time of JWT) in : `src/main/resources/application.properties`

#### Front End
You can find a sample front end implementation for this project in repository, made with angular and used ngx-admin as an open source template.

#### Development

This project is made, built and test with Spring Tool Suite 4.

## Production

This is NOT a project intented for production usage. It is just a simple project for testing RBAC pattern and JWt authentication, two common patterns in modern web application development.


## License


**Of course usage is free for everyone!**

