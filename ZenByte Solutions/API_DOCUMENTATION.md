# ZenByte Solutions - API Documentation

## Base URL
- Development (Node.js gateway): `http://localhost:3000/api`
- Development (Java direct): `http://localhost:8080/api`
- Production: `https://zenbytesolutions.in/api`

## Node.js Gateway Endpoints

### Health Check
**Endpoint:** `GET /api/health`

Returns combined status of Node.js gateway and Java API.

### Application Info
**Endpoint:** `GET /api/info`

Returns stack information (Node.js + Java + MySQL).

---

## Java API Endpoints (proxied via Node.js)

## Authentication
Currently, the API supports optional JWT authentication. Include the token in the header:
```
Authorization: Bearer {jwt_token}
```

## Response Format
All responses are in JSON format.

### Success Response
```json
{
  "id": 1,
  "message": "Operation successful",
  "data": {}
}
```

### Error Response
```json
{
  "error": "Error message",
  "status": 400,
  "timestamp": "2026-06-15T10:30:00"
}
```

---

## Contacts API

### 1. Create Contact
**Endpoint:** `POST /contacts`

**Request Body:**
```json
{
  "fullName": "John Doe",
  "email": "john@example.com",
  "phone": "+91-9876543210",
  "subject": "Web Development Inquiry",
  "service": "Web Development",
  "message": "I'm interested in your web development services"
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "fullName": "John Doe",
  "email": "john@example.com",
  "phone": "+91-9876543210",
  "subject": "Web Development Inquiry",
  "service": "Web Development",
  "message": "I'm interested in your web development services",
  "status": "NEW",
  "createdAt": "2026-06-15T10:30:00"
}
```

### 2. Get All Contacts
**Endpoint:** `GET /contacts`

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "fullName": "John Doe",
    "email": "john@example.com",
    ...
  }
]
```

### 3. Get Contact by ID
**Endpoint:** `GET /contacts/{id}`

**Response:** `200 OK`
```json
{
  "id": 1,
  "fullName": "John Doe",
  ...
}
```

### 4. Get Contacts by Status
**Endpoint:** `GET /contacts/status/{status}`

**Parameters:**
- `status`: NEW, REVIEWED, RESOLVED, REJECTED

**Response:** `200 OK`

### 5. Update Contact
**Endpoint:** `PUT /contacts/{id}`

**Request Body:** (All fields optional)
```json
{
  "status": "REVIEWED",
  "message": "Updated message"
}
```

**Response:** `200 OK`

### 6. Delete Contact
**Endpoint:** `DELETE /contacts/{id}`

**Response:** `204 No Content`

---

## Quote Requests API

### 1. Create Quote Request
**Endpoint:** `POST /quotes`

**Request Body:**
```json
{
  "companyName": "Tech Corp",
  "contactName": "Jane Smith",
  "contactEmail": "jane@techcorp.com",
  "projectType": "Web Application",
  "budget": "100000-500000",
  "projectDescription": "We need a complete web application for our business"
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "companyName": "Tech Corp",
  "contactName": "Jane Smith",
  "contactEmail": "jane@techcorp.com",
  "projectType": "Web Application",
  "budget": "100000-500000",
  "projectDescription": "We need a complete web application for our business",
  "status": "PENDING",
  "createdAt": "2026-06-15T10:30:00"
}
```

### 2. Get All Quote Requests
**Endpoint:** `GET /quotes`

**Response:** `200 OK`

### 3. Get Quote Request by ID
**Endpoint:** `GET /quotes/{id}`

**Response:** `200 OK`

### 4. Get Quotes by Status
**Endpoint:** `GET /quotes/status/{status}`

**Parameters:**
- `status`: PENDING, SENT, ACCEPTED, REJECTED

**Response:** `200 OK`

### 5. Update Quote Request
**Endpoint:** `PUT /quotes/{id}`

**Request Body:**
```json
{
  "status": "SENT"
}
```

**Response:** `200 OK`

### 6. Delete Quote Request
**Endpoint:** `DELETE /quotes/{id}`

**Response:** `204 No Content`

---

## Job Applications API

### 1. Submit Job Application
**Endpoint:** `POST /applications`

**Request Body:**
```json
{
  "fullName": "Alex Johnson",
  "email": "alex@example.com",
  "phone": "+91-9999999999",
  "position": "Senior Java Developer",
  "yearsOfExperience": 6,
  "coverLetter": "I am very interested in this position..."
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "fullName": "Alex Johnson",
  "email": "alex@example.com",
  "phone": "+91-9999999999",
  "position": "Senior Java Developer",
  "yearsOfExperience": 6,
  "coverLetter": "I am very interested in this position...",
  "status": "SUBMITTED",
  "createdAt": "2026-06-15T10:30:00"
}
```

### 2. Get All Job Applications
**Endpoint:** `GET /applications`

**Response:** `200 OK`

### 3. Get Application by ID
**Endpoint:** `GET /applications/{id}`

**Response:** `200 OK`

### 4. Get Applications by Status
**Endpoint:** `GET /applications/status/{status}`

**Parameters:**
- `status`: SUBMITTED, REVIEWED, SHORTLISTED, REJECTED, HIRED

**Response:** `200 OK`

### 5. Get Applications by Position
**Endpoint:** `GET /applications/position/{position}`

**Response:** `200 OK`

### 6. Update Application
**Endpoint:** `PUT /applications/{id}`

**Request Body:**
```json
{
  "status": "SHORTLISTED"
}
```

**Response:** `200 OK`

### 7. Delete Application
**Endpoint:** `DELETE /applications/{id}`

**Response:** `204 No Content`

---

## HTTP Status Codes

| Code | Meaning |
|------|---------|
| 200  | OK - Request successful |
| 201  | Created - Resource created successfully |
| 204  | No Content - Request successful, no content to return |
| 400  | Bad Request - Invalid request |
| 401  | Unauthorized - Authentication required |
| 403  | Forbidden - Access denied |
| 404  | Not Found - Resource not found |
| 500  | Internal Server Error - Server error |

---

## Example Requests using cURL

### Create Contact
```bash
curl -X POST http://localhost:8080/api/contacts \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "John Doe",
    "email": "john@example.com",
    "phone": "+91-9876543210",
    "subject": "Web Development",
    "service": "Web Development",
    "message": "Hello, I need web development services"
  }'
```

### Get All Contacts
```bash
curl -X GET http://localhost:8080/api/contacts
```

### Update Contact Status
```bash
curl -X PUT http://localhost:8080/api/contacts/1 \
  -H "Content-Type: application/json" \
  -d '{
    "status": "REVIEWED"
  }'
```

### Delete Contact
```bash
curl -X DELETE http://localhost:8080/api/contacts/1
```

---

## Rate Limiting
- Planned: Implement rate limiting (e.g., 100 requests per minute per IP)

## Pagination
- Planned: Add pagination support for large datasets

## Filtering & Sorting
- Planned: Add advanced filtering and sorting options

## Webhooks
- Planned: Add webhook support for real-time notifications

---

## Error Handling

### Common Errors

**400 Bad Request**
```json
{
  "error": "Invalid email format",
  "status": 400
}
```

**404 Not Found**
```json
{
  "error": "Contact not found",
  "status": 404
}
```

**500 Internal Server Error**
```json
{
  "error": "Internal server error",
  "status": 500
}
```

---

## Testing with Postman

1. Import collection from API documentation
2. Set environment variables (base URL, etc.)
3. Test each endpoint
4. Verify response status codes
5. Check response data format

---

## API Versioning

Currently on version 1.0. Future versions will be available at `/v2/`, `/v3/`, etc.

---

## Support

For API support and issues, contact: api@zenbytesolutions.com
