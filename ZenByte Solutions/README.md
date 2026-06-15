# ZenByte Solutions — Full Stack Project

**Node.js + Java Spring Boot + MySQL**

| Layer | Technology | Role |
|-------|------------|------|
| **Frontend** | HTML, CSS, Bootstrap 5, JavaScript | User interface |
| **Node.js** | Express 4 | Web server, static files, API gateway |
| **Java** | Spring Boot 3 | REST API, business logic, database |
| **Database** | MySQL 8 | Data persistence |

## Architecture

```
Browser
   │
   ▼
Node.js Express  (:3000 / :80)
   ├── Serves frontend static files
   ├── /api/health, /api/info  → Node.js routes
   └── /api/*                  → Proxy → Java Spring Boot (:8080)
                                              │
                                              ▼
                                         MySQL (:3306)
```

## Project Structure

```
ZenByte Solutions/
├── nodejs/                 # Node.js Express gateway
│   ├── src/
│   │   ├── server.js       # Main server
│   │   ├── config.js       # Environment config
│   │   └── routes/
│   │       └── health.js   # Health & info endpoints
│   ├── package.json
│   └── Dockerfile
├── backend/                # Java Spring Boot API
│   └── src/main/java/com/zenbyte/
├── frontend/               # Static website
├── database/               # MySQL schema
├── docker-compose.yml      # Local full stack
├── docker-compose.prod.yml # Production (Hostinger VPS)
└── package.json            # Root scripts (run both stacks)
```

## Quick Start

### Prerequisites
- Node.js 18+
- Java 17+
- Maven 3.6+
- MySQL 8.0+

### 1. Install dependencies
```bash
npm run install:all
```

### 2. Set up database
```bash
mysql -u root -p < database/schema.sql
```

### 3. Configure Java backend
Edit `backend/src/main/resources/application.properties` with your MySQL credentials.

### 4. Run full stack (both servers)
```bash
npm run dev
```

| Service | URL |
|---------|-----|
| Website | http://localhost:3000 |
| Node health | http://localhost:3000/api/health |
| Java API (via proxy) | http://localhost:3000/api/contacts |
| Java API (direct) | http://localhost:8080/api/contacts |

### Run individually
```bash
npm run dev:java    # Java only (port 8080)
npm run dev:node    # Node.js only (port 3000)
```

## Docker (recommended)

```bash
docker compose up -d --build
```

- Website: http://localhost:3000
- Health: http://localhost:3000/api/health

## API Endpoints

### Node.js (Gateway)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/health` | Combined Node + Java health status |
| GET | `/api/info` | Application stack info |

### Java Spring Boot (proxied via `/api`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/contacts` | Submit contact form |
| POST | `/api/quotes` | Request a quote |
| POST | `/api/applications` | Submit job application |
| GET | `/api/contacts` | List contacts |

See [API_DOCUMENTATION.md](API_DOCUMENTATION.md) for full details.

## Production Deployment

Deploy to **zenbytesolutions.in** on Hostinger VPS — see [HOSTINGER_DEPLOY.md](HOSTINGER_DEPLOY.md).

```bash
./deploy/hostinger/one-click-deploy.sh
```

Production uses **Node.js on port 80** proxying to Java internally.

## Environment Variables

### Node.js (`nodejs/.env`)
```env
PORT=3000
JAVA_API_URL=http://localhost:8080
NODE_ENV=development
DOMAIN=zenbytesolutions.in
```

### Java (`backend/application.properties`)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/zenbyte_db
spring.datasource.username=zenbyte
spring.datasource.password=your_password
```

---

**ZenByte Solutions** — Innovate • Integrate • Elevate
