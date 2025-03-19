Authentication gateway provides a secure REST API for user management with PostgreSQL storage. The API includes four key endpoints:

- `/signup`: Handles new user registration, hashing passwords with BCrypt, and storing user credentials in PostgreSQL users table.

- `/signin`: Authenticates users by verifying submitted credentials against stored hashed passwords, generating JWT tokens upon successful authentication for subsequent requests.

- `/public`: Provides unrestricted access.

- `/authenticated`:  Requiring valid JWT tokens in request headers, verifying token integrity before allowing.
