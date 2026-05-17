# Homiko System (Docker Compose)

Stage, prod ve Grafana Loki stack yapılandırması.

## Kurulum

Proje kökünden:

```powershell
powershell -ExecutionPolicy Bypass -File .github\homiko-system\setup.ps1
```

Bu komut tüm dosyaları `./homiko-system` altına kopyalar.

## Çalıştırma

```powershell
cd homiko-system
copy .env.example .env
docker compose pull
docker compose up -d
```

| Servis | URL |
|--------|-----|
| Prod | http://localhost:8100 |
| Stage | http://localhost:8200 |
| Grafana | http://localhost:3000 |

## Grafana log sorguları

- `{environment="prod"}`
- `{environment="stage"}`
