# **Rentit.com: Connecting Owners and Renters**

**Rentit.com** is a platform that connects item owners and renters. Users can rent items for flexible durations ranging from days to weeks, or even longer, depending on the agreement between both parties, all facilitated and mediated by Rentit.com.

This platform leverages a **microservices architecture** to provide flexibility, clearer boundaries between services, and easier collaboration. This modular approach ensures the platform is scalable, maintainable, and capable of handling future growth efficiently.

---

## **Architecture Overview**

Rentit.com follows a microservices architecture, primarily consisting of **7 key microservices**, each responsible for different aspects of the application. This separation allows independent scaling, ease of maintenance, and modular development.

### **Microservices Breakdown**

### 1. **User Onboarding Monolith Service - CORE**
This service manages all user-related functionality and consumes the services of other microservices.

- **Responsibilities**:
  - User registration, login, and logout.
  - Profile management (e.g., updating profile information, managing preferences).
  - Role-based access control (admin, user).
  - Authentication and security management.
  - provides platform for further listing/booking based microservice consumption
  
- **Endpoints**:
  - `POST /users/register` – Register a new user.
  - `POST /users/login` – User login.
  - `GET /users/profile/{userId}` – Retrieve user profile by ID.
  - `PUT /users/update` – Update user information.

- **Database**: MongoDB – User entity is managed using MongoDB - Stores the user data in a collection "users"

- **Security**: Implemented via JWT-based authentication.

---

### 2. **Listing Microservice**
This service handles the creation and management of rental listings.

- **Responsibilities**:
  - Create, update, and delete item listings.
  - Fetch item details.
  - Filter and search through listings based on various criteria (e.g., location, price).

- **Endpoints**:
  - `POST /listings/create` – Create a new listing.
  - `GET /listings/{listingId}` – Fetch a listing by ID.
  - `GET /listings/search?query={criteria}` – Search for listings based on criteria.
  - `PUT /listings/{listingId}/update` – Update a listing.
  - `DELETE /listings/{listingId}/delete` – Delete a listing.

- **Database**: MongoDB – Listing entity is managed using MongoDB - Stores the listed items data in a collection "listings"

- **Features**:
  - Support for image uploads for listed items.
  - Real-time availability updates.

---

### 3. **Booking Microservice**
This service manages the rental or booking process, linking users and items.

- **Responsibilities**:
  - Handle requests to rent items.
  - Track rental status (pending, accepted, active, completed).
  - Manage rental periods, fees, and conditions.

- **Endpoints**:
  - `POST /bookings/create` – Create a new rental request.
  - `GET /bookings/{bookingId}` – Get rental details by ID.
  - `PUT /bookings/{bookingId}/updateStatus` – Update rental status.
  - `GET /bookings/user/{userId}` – Get all rentals by a user.

- **Database**: MongoDB – Rental entity. - Stores the booking data in a collection "bookings"

- **Features**:
  - Validate item availability during the rental period.
  - Maintain a rental history for users.
  - Synchronizes with the Notification Service and the Payment Service

---

### 4. **Payment Microservice**
This service handles all payment-related functionality between users.

- **Responsibilities**:
  - Manage the payment process, including transaction creation and fee calculation.
  - Integrate with third-party payment gateways.
  - Calculate platform fees for each rental.
  - Store and manage transaction history.

- **Endpoints**:
  - `POST /payments/process` – Process a payment.
  - `GET /payments/{transactionId}` – Get payment details by transaction ID.
  - `GET /payments/history/user/{userId}` – Get payment history for a user.

- **Database**: MongoDB – Payment entity (fields like amount, user, payment method, transaction status).
  - Fee entity (for tracking platform fees).

- **Security**: Ensure secure payment processing and data encryption.

---

### 5. **Notification Service**
This service is responsible for real-time communication with users (e.g., email or push notifications).

- **Responsibilities**:
  - Send notifications when a rental is created, updated, or completed.
  - Notify users of system events (e.g., successful payment, item availability updates).
  - Send reminders for upcoming rental deadlines or expirations.

- **Endpoints**:
  - `POST /notifications/send` – Send a notification.
  - `GET /notifications/user/{userId}` – Get notifications for a specific user.

- **Database**: MongoDB – Notification entity (fields like message content, recipient, read/unread status).

- **Features**:
  - Use WebSockets (e.g., Socket.IO in Node.js) for real-time notifications.
  - Implement email notifications.

---

### 6. **Review and Rating Service**
This service enables users to leave reviews and ratings for items and other users.

- **Responsibilities**:
  - Allow users to rate and review items after rentals are completed.
  - Calculate average ratings for items and users.
  - Moderate user feedback (e.g., flag inappropriate content).

- **Endpoints**:
  - `POST /reviews/{itemId}` – Submit a review for an item.
  - `GET /reviews/item/{itemId}` – Get reviews for an item.
  - `GET /reviews/user/{userId}` – Get reviews for a user.

- **Database**: MongoDB – Review entity (fields like reviewer, item, rating, comments, timestamps).

- **Features**:
  - Implement a star rating system (1–5 stars).
  - Display average ratings for each item or user profile.

---

### 7. **Search and Recommendation Service**
This service handles search functionality and recommends items to users.

- **Responsibilities**:
  - Perform full-text searches on item listings (based on keywords, categories, etc.).
  - Recommend items to users based on browsing history, ratings, or past rentals.

- **Endpoints**:
  - `GET /search?query={keywords}` – Perform a search based on keywords.
  - `GET /recommendations/user/{userId}` – Get item recommendations for a user.

- **Database**: MongoDB
  - Search history entity (tracks user search actions).
  - Recommendation entity (stores personalized suggestions).

- **Features**:
  - Use Elasticsearch or similar tools for efficient search indexing.
  - Implement a recommendation algorithm (e.g., collaborative filtering, content-based).

---

## **Microservice Architecture Benefits**
- **Scalability**: Each microservice can be scaled independently, allowing the platform to handle increasing traffic efficiently.
- **Maintainability**: The separation of concerns ensures that each service is self-contained, making it easier to maintain, update, and debug.
- **Flexibility**: Teams can work on different services simultaneously without stepping on each other’s toes, enabling faster development cycles.

---

