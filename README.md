# Order Management Backend (Spring Boot + AWS)

This is the backend part of my Real-Time Order Management System built with Spring Boot. I’ve integrated this with AWS services like DynamoDB (for data storage), S3 (for invoice uploads), and SNS (for sending order notifications).

## ✅ Features

- REST APIs to create, view, update, and delete orders.
- Invoice upload using AWS S3 (returns downloadable link).
- Order notifications through AWS SNS.
- DynamoDB used as the primary database.
- CORS configured to work with deployed frontend.

## 🛠 Tech Stack

- Java 17
- Spring Boot 3.2.x
- AWS SDK v2 (DynamoDB, S3, SNS)
- Render (for deployment)

## 🚀 Deployment

The backend is deployed live on Render:

🔗 [Backend Live URL](https://order-backend-deploy.onrender.com/api/orders)

## 📁 Endpoints

| Method | Endpoint                        | Description                   |
|--------|----------------------------------|-------------------------------|
| POST   | `/api/orders`                   | Create a new order            |
| GET    | `/api/orders`                   | Get all orders                |
| GET    | `/api/orders/{id}`              | Get specific order by ID      |
| PUT    | `/api/orders/{id}`              | Update an order               |
| DELETE | `/api/orders/{id}`              | Delete an order               |
| POST   | `/api/orders/{id}/upload-invoice` | Upload invoice to S3         |

## 📄 How I Built It

I started by designing the `Order` model and then set up the AWS SDK clients for DynamoDB, SNS, and S3. I used the `DynamoDbEnhancedClient` to interact with DynamoDB in a cleaner way.

I faced several issues with environment variables and AWS credentials while deploying to Render but figured out how to set them securely using Render’s environment settings.

## 🔒 Environment Variables (used on Render)

- `AWS_ACCESS_KEY`
- `AWS_SECRET_KEY`
- `AWS_REGION`
- `aws.sns.topic.arn`

---

