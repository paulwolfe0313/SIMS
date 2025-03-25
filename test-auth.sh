#!/bin/bash

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'

# Auth service URL
AUTH_URL="http://localhost:9093"
PRODUCT_URL="http://localhost:9090"

# Test data
EMAIL="test@example.com"
PASSWORD="Test123!"
NEW_PASSWORD="NewTest123!"
FIRST_NAME="John"
LAST_NAME="Doe"

echo -e "${GREEN}Starting Security Features Test${NC}\n"

# 1. User Registration
echo -e "${GREEN}Testing User Registration...${NC}"
REGISTER_RESPONSE=$(curl -s -X POST "${AUTH_URL}/auth/register" \
  -H "Content-Type: application/json" \
  -d "{
    \"email\": \"${EMAIL}\",
    \"password\": \"${PASSWORD}\",
    \"firstName\": \"${FIRST_NAME}\",
    \"lastName\": \"${LAST_NAME}\"
  }")
echo "Registration Response: $REGISTER_RESPONSE"
echo

# 2. Login (Authentication)
echo -e "${GREEN}Testing Login...${NC}"
LOGIN_RESPONSE=$(curl -s -X POST "${AUTH_URL}/auth/login" \
  -H "Content-Type: application/json" \
  -d "{
    \"email\": \"${EMAIL}\",
    \"password\": \"${PASSWORD}\"
  }")
echo "Login Response: $LOGIN_RESPONSE"

# Extract token from login response
TOKEN=$(echo $LOGIN_RESPONSE | grep -o '"token":"[^"]*' | cut -d'"' -f4)
echo "JWT Token: $TOKEN"
echo

# 3. Access Protected Resource (Products)
echo -e "${GREEN}Testing Protected Resource Access...${NC}"
PRODUCTS_RESPONSE=$(curl -s -X GET "${PRODUCT_URL}/products" \
  -H "Authorization: Bearer ${TOKEN}")
echo "Protected Resource Response: $PRODUCTS_RESPONSE"
echo

# 4. Password Reset Request
echo -e "${GREEN}Testing Password Reset Request...${NC}"
RESET_REQUEST_RESPONSE=$(curl -s -X POST "${AUTH_URL}/auth/forgot-password" \
  -H "Content-Type: application/json" \
  -d "{
    \"email\": \"${EMAIL}\"
  }")
echo "Password Reset Request Response: $RESET_REQUEST_RESPONSE"
echo

# Note: Since email verification and actual password reset require tokens sent via email,
# we can't fully automate those tests. Manual verification required.
echo -e "${GREEN}Manual Verification Required:${NC}"
echo "1. Check your email for verification token"
echo "2. Use the verification token to verify email: ${AUTH_URL}/auth/verify-email?token=<verification_token>"
echo "3. Check your email for password reset token"
echo "4. Use the reset token to reset password: POST ${AUTH_URL}/auth/reset-password"
echo

# 5. Change Password (requires authentication)
echo -e "${GREEN}Testing Password Change...${NC}"
CHANGE_PASSWORD_RESPONSE=$(curl -s -X POST "${AUTH_URL}/auth/change-password" \
  -H "Authorization: Bearer ${TOKEN}" \
  -H "Content-Type: application/json" \
  -d "{
    \"currentPassword\": \"${PASSWORD}\",
    \"newPassword\": \"${NEW_PASSWORD}\"
  }")
echo "Change Password Response: $CHANGE_PASSWORD_RESPONSE"
echo

echo -e "${GREEN}Security Features Test Complete${NC}"
echo "Please check the responses above for any errors"
