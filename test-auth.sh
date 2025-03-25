#!/bin/bash

echo "1. Registering a new user..."
curl -X POST http://localhost:9093/auth/register \
-H "Content-Type: application/json" \
-d '{
    "email": "test@example.com",
    "password": "password123",
    "firstName": "Test",
    "lastName": "User"
}'
echo -e "\n"

echo "2. Logging in..."
TOKEN=$(curl -X POST http://localhost:9093/auth/login \
-H "Content-Type: application/json" \
-d '{
    "email": "test@example.com",
    "password": "password123"
}' | grep -o '"token":"[^"]*' | cut -d'"' -f4)
echo "Token: $TOKEN"
echo -e "\n"

echo "3. Testing protected endpoint (products service)..."
curl -H "Authorization: Bearer $TOKEN" http://localhost:9090/api/products
echo -e "\n"
