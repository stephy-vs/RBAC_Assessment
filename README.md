#**Food Delivery App (RBAC-Based)**

This is a food delivery application built with Role-Based Access Control (RBAC). The application allows three types of users (Admin, Restaurant, and User) to perform specific actions based on their roles.
**Features**
Roles and Permissions
1. Admin

Add and manage Category Types (e.g., Veg, Non-Veg).
Add and manage Categories (in collaboration with restaurants).
Accept registered restaurant by verifying their details.
Delete Restaurant.
Delete any Dish.
View all registered restaurants and user details.

2. Restaurant

Add, update, and delete their own Dishes.
Collaborate with Admin to manage Categories.

3. User

Add Dishes to the cart.
View all items in their cart.
Update the quantity of dishes in the cart.
Delete items from their cart.

**Tech Stack**
Backend: [Spring Boot]
Database: [PostgreSQL]
Authentication: [JWT]

**API Endpoints**
Admin
Login - Post /api/admin/login
"email":"admindefault@gmail.com",
"password":"password"


Add CategoryType - Post /api/type/add
Add Category - Post /api/category/add
Get all User - Get /api/admin/getAllUser
Get all Restaurant - Get /api/admin/getAllRestaurant
Accept Restaurant Registration - Put /api/admin/updateStatus
Delete Restaurant Account - Delete /api/admin/deleteAccount/{id}
Block user Account - Put /api/admin/updateUserStatus

Restaurant
Signup - Post /api//restaurant/signUp
Login - Post /api//restaurant/login
Add Dish - Post /api/dish/add
Update Dish - Put /api/dish/update
Delete Dish - Delete /api/dish/delete/{id}

User
Signup -Post /api/user/signup
SignIn - Post  /api/user/login
Add dishes to the cart - Post /api/cart/addItems
Get all cart items - Get /api/cart/get
Update cart-Item quantity - Put /api/cart/updateCartQty/{userId}{cartId}/{qty}
Delete cart-Item - Delete /api/cart/deleteItem/{userId}/{cartId}

**Common API Endpoints**
Get CategoryType - Get /api/type/get
Get Category - Get /api/category/get
Get Dish - Get /api/dish/get
 