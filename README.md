## Starta projektet

Krav: Java 21.

**Windows (PowerShell):**

```powershell
.\mvnw.cmd spring-boot:run
```

**macOS / Linux:**

```bash
chmod +x run.sh
./run.sh
```

Applikationen startar på `http://localhost:8080`.

## Starta projektet

Krav: Java 21.

**Windows (PowerShell):**

```powershell
.\mvnw.cmd spring-boot:run
```

**macOS / Linux:**

```bash
chmod +x run.sh
./run.sh
```

Applikationen startar på `http://localhost:8080`.

## Endpoints 

Base URL: `http://localhost:8080`

| Method | Endpoint | Description |
|---|---|---|
| POST | `/customers?name=Anna&phone=123456789` | Create customer |
| GET | `/customer-id?name=Anna` | Get customer ID by name |
| GET | `/customers` | Get all customers with vehicles |
| POST | `/vehicles?registrationNumber=ABC123&brand=Volvo&model=V60&productionYear=2022` | Create vehicle |
| GET | `/vehicles` | Get all vehicles |
| GET | `/vehicles-by-brand?brand=Volvo` | Get vehicles by brand |
| POST | `/addvehicletocustomer?customerId=1&vehicleId=1` | Connect vehicle to customer |
| PUT | `/vehicles/1/brand?brand=Saab` | Update vehicle brand |
| GET | `/vehicle-brand-info` | Get vehicle brand information using Projection |

## Examples of requests

```bash
# Create customer
curl -X POST "http://localhost:8080/customers?name=Anna&phone=123456789"

# Get customer ID
curl "http://localhost:8080/customer-id?name=Anna"

# Get all customers
curl "http://localhost:8080/customers"

# Create vehicle
curl -X POST "http://localhost:8080/vehicles?registrationNumber=ABC123&brand=Volvo&model=V60&productionYear=2022"

# Get all vehicles
curl "http://localhost:8080/vehicles"

# Get vehicles by brand
curl "http://localhost:8080/vehicles-by-brand?brand=Volvo"

# Connect vehicle to customer
curl -X POST "http://localhost:8080/addvehicletocustomer?customerId=1&vehicleId=1"

# Update vehicle brand
curl -X PUT "http://localhost:8080/vehicles/1/brand?brand=Saab"

# Get vehicle brand information
curl "http://localhost:8080/vehicle-brand-info"
```