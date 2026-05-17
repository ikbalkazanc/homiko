# Homiko - Spring Boot + Kotlin + PostgreSQL

Kotlin ve Spring Boot ile geliştirilmiş örnek bir REST API uygulaması.

## Teknolojiler

- **Kotlin** 1.9.25
- **Spring Boot** 3.5.14
- **PostgreSQL** 17
- **Liquibase** (Database Migration)
- **Spring Data JPA**
- **Docker & Docker Compose**

## Özellikler

- User CRUD API (GET, POST, DELETE)
- Liquibase ile otomatik database migration
- Stage ve Production ortamları için ayrı database'ler
- Grafana + Loki ile logging
- Watchtower ile otomatik güncelleme

## Kurulum

### 1. Yerel Geliştirme

```bash
# PostgreSQL'i Docker ile başlatın
cd .github/homiko-system
docker compose up -d postgres

# Uygulamayı çalıştırın
./gradlew bootRun --args='--spring.profiles.active=stage'
```

### 2. Docker Compose ile Tüm Sistem

```bash
cd .github/homiko-system
cp .env.example .env  # (eğer yoksa)
docker compose up -d
```

## API Endpoints

### User API

#### Tüm kullanıcıları listele
```bash
GET /api/users
```

**Response:**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "createdAt": "2026-05-18T00:00:00Z",
    "updatedAt": "2026-05-18T00:00:00Z"
  }
]
```

#### Kullanıcıyı ID ile getir
```bash
GET /api/users/{id}
```

#### Yeni kullanıcı oluştur
```bash
POST /api/users
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "createdAt": "2026-05-18T00:00:00Z",
  "updatedAt": "2026-05-18T00:00:00Z"
}
```

#### Kullanıcıyı sil
```bash
DELETE /api/users/{id}
```

**Response:** 204 No Content

### Diğer Endpoints

```bash
GET /api/hello      # Merhaba mesajı
GET /api/info       # Uygulama bilgisi
GET /api/version    # Versiyon bilgisi
```

## Test

### cURL ile test

```bash
# Stage ortamında (port 8200)
# Kullanıcı oluştur
curl -X POST http://localhost:8200/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Ali Veli","email":"ali@example.com"}'

# Tüm kullanıcıları listele
curl http://localhost:8200/api/users

# Kullanıcıyı ID ile getir
curl http://localhost:8200/api/users/1

# Kullanıcıyı sil
curl -X DELETE http://localhost:8200/api/users/1
```

```bash
# Production ortamında (port 8100)
curl -X POST http://localhost:8100/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Ayşe Yılmaz","email":"ayse@example.com"}'
```

## Database

### Ortamlar

- **Stage Database:** `homiko-stage` (port 5432)
- **Production Database:** `homiko-prod` (port 5432)

Her iki ortam da aynı PostgreSQL container'ına bağlanır ama farklı database'ler kullanır.

### PostgreSQL'e bağlanma

```bash
# Stage database
docker exec -it homiko-postgres psql -U homiko -d homiko-stage

# Production database
docker exec -it homiko-postgres psql -U homiko -d homiko-prod

# Kullanıcıları listele
SELECT * FROM users;
```

## Monitoring

- **Grafana:** http://localhost:3000 (admin/changeme)
- **Loki:** http://localhost:3100

## Deployment

Docker image'ları:
- **Stage:** `ikbalkazanc/homiko:stage`
- **Production:** `ikbalkazanc/homiko:prod`

Watchtower otomatik olarak güncellemeleri kontrol eder ve uygular.

## Liquibase Migrations

Migration dosyaları `src/main/resources/db/changelog/` klasöründe bulunur.

Yeni migration eklemek için:
1. `db/changelog/changes/` altında yeni bir YAML dosyası oluşturun
2. `db.changelog-master.yaml` dosyasına ekleyin

## Geliştirme

```bash
# Build
./gradlew build

# Test
./gradlew test

# Docker image oluştur
./gradlew bootBuildImage
```
