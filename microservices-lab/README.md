# Microservices Lab: Java 21 · Spring Boot 3.5 · Spring Cloud 2025.0

A small but complete microservices backend built to be **studied in about one hour**.
Every file is short, commented, and mapped to an interview concept below.

```
                        Client (curl / browser)
                                 │  :8080
                                 ▼
                        ┌─────────────────┐
                        │   api-gateway   │  routes, global filter, lb://
                        └───┬─────────┬───┘
                 lb://product│         │lb://order
                            ▼         ▼
              ┌─────────────────┐   ┌─────────────────┐
              │ product-service │◀──│  order-service  │
              │  :8081 · JPA    │   │  :8082 · JPA    │
              └─────────────────┘   └─────────────────┘
                     Feign + circuit breaker + fallback

  Every service ──register / discover──▶ discovery-server :8761 (Eureka)
  Every service ──fetch config at boot─▶ config-server    :8888 ──▶ config-repo/*.yml
```

| Module | Port | Role |
|---|---|---|
| `config-server` | 8888 | Serves `config-repo/` over HTTP (native/file backend; Git in prod) |
| `discovery-server` | 8761 | Eureka service registry + dashboard |
| `product-service` | 8081 | CRUD + stock reservation. JPA, validation, tests, `@RefreshScope` |
| `order-service` | 8082 | Places orders by calling product-service via **Feign** |
| `api-gateway` | 8080 | Single entry point (Spring Cloud Gateway, reactive) |

## What happens when a service boots

1. Local `application.yml` contains only `spring.application.name` and `spring.config.import=configserver:...`.
2. The service calls `GET config-server/{name}/{profile}` and receives the merged property sources
   (`application.yml` + `{name}.yml` + `{name}-{profile}.yml`). Port, datasource and Eureka URL arrive here.
3. Boot starts with those values. The Eureka client registers the instance and sends a heartbeat every 30 s.
4. When order-service calls `product-service`, Feign asks Spring Cloud LoadBalancer, which picks an instance
   (round-robin) from its **local copy** of the registry (refreshed every 30 s).

## Concept map

| Concept | Where to look |
|---|---|
| Auto-configuration, starters, `@SpringBootApplication` | every `*Application.java`, `pom.xml` |
| Layered architecture, constructor injection | `ProductController` → `ProductService` → `ProductRepository` |
| Spring Data JPA: derived query, `@Query`, dirty checking, `@Version` | `ProductRepository`, `Product`, `ProductService.update/reserve` |
| `@Transactional` (class-level `readOnly`, method override) | `ProductService`; `OrderService` (why NOT on `placeOrder`) |
| DTO records + Bean Validation | `web/dto/*`, `@Valid` in controllers |
| Global error handling with `ProblemDetail` (RFC 7807) | `web/GlobalExceptionHandler` |
| `@ConfigurationProperties` (typed) vs `@Value` | `ProductProperties`, `OrderProperties` vs `InfoController` |
| Profiles | `DataSeeder` (`@Profile("!prod")`), `config-repo/product-service-prod.yml` |
| Actuator | `/actuator/health`, `/actuator/refresh` (config in `config-repo/application.yml`) |
| **Config Server** + client import | `config-server/`, `config-repo/`, each service's `application.yml` |
| **`@RefreshScope`**, live config reload | `InfoController` + experiment 5 |
| **Eureka** server / client, registration | `discovery-server/`, shared `eureka.*` config |
| Client-side load balancing | `lb://` in gateway routes, `@FeignClient(name=...)`, experiment 6 |
| **Feign**: interface client, timeouts, logging, interceptor | `ProductClient`, `FeignConfig`, `config-repo/order-service.yml` |
| **Circuit breaker** (Resilience4j), timeout, fallback | `ResilienceConfig`, `ProductClientFallbackFactory`, experiment 7 |
| **API Gateway**: routes, predicates, filters, global filter | `config-repo/api-gateway.yml`, `RequestIdFilter` |
| Testing pyramid: unit / `@WebMvcTest` / `@DataJpaTest` | `src/test/**` in both services |
| Java 21: records, pattern-matching `switch`, text blocks, virtual threads | DTOs, `ProductClientFallbackFactory.translate`, tests, `spring.threads.virtual.enabled` |

## Run it

Prerequisites: **JDK 21**, **Maven 3.9+**. Run from the project root.

```bash
mvn -DskipTests package        # optional: compile everything first
mvn test                       # optional: run all tests (no infrastructure needed)
```

Start each module in its **own terminal, in this order** (config first, because the others need it at boot):

```bash
mvn -pl config-server    spring-boot:run     # 1. http://localhost:8888
mvn -pl discovery-server spring-boot:run     # 2. http://localhost:8761
mvn -pl product-service  spring-boot:run     # 3. :8081
mvn -pl order-service    spring-boot:run     # 4. :8082
mvn -pl api-gateway      spring-boot:run     # 5. :8080
```

Open the Eureka dashboard at http://localhost:8761. After ~30 s you should see `PRODUCT-SERVICE`,
`ORDER-SERVICE` and `API-GATEWAY` registered.

> IDE note: the config server reads `../config-repo/` relative to its working directory. If your IDE runs it from the
> project root, set `CONFIG_REPO_LOCATION=file:./config-repo/` (trailing slash required).

Seed data (product-service): `1` Keyboard (25 in stock) · `2` Mouse (100) · `3` Monitor (4) · `4` USB-C Cable (0).

## Experiments (do these, in order)

**1. See what the Config Server serves**
```bash
curl -s localhost:8888/product-service/default | python3 -m json.tool | head -40
curl -s localhost:8888/product-service/prod    | python3 -m json.tool | grep '"name"'   # prod file layered on top
```

**2. Happy path through the gateway**
```bash
curl -i localhost:8080/api/products
curl -i -X POST localhost:8080/api/orders -H 'Content-Type: application/json' -d '{"productId":1,"quantity":2}'
curl -s localhost:8080/api/orders/1/details      # order + live product data (Feign GET)
curl -s localhost:8080/api/products/1            # stock dropped from 25 to 23
```
Look at the response headers: `X-Gateway` (route default filter) and `X-Request-Id` (global filter).

**3. Business errors (each maps to a different status via `ProblemDetail`)**
```bash
curl -i -X POST localhost:8080/api/orders -H 'Content-Type: application/json' -d '{"productId":3,"quantity":5}'    # 409 only 4 in stock
curl -i -X POST localhost:8080/api/orders -H 'Content-Type: application/json' -d '{"productId":999,"quantity":1}'  # 404
curl -i -X POST localhost:8080/api/orders -H 'Content-Type: application/json' -d '{"productId":1,"quantity":11}'   # 400 max is 10 (from config)
curl -i -X POST localhost:8080/api/products -H 'Content-Type: application/json' -d '{"name":"","price":-5,"stock":1}' # 400 + errors map
```

**4. Query methods**
```bash
curl -s 'localhost:8080/api/products?name=MOUSE'         # derived query, case-insensitive
curl -s  localhost:8080/api/products/low-stock            # threshold comes from config (product.low-stock-threshold)
curl -s 'localhost:8080/api/products/affordable?max=50'   # @Query JPQL
```

**5. Change config without restarting** (`@RefreshScope`)
```bash
curl -s localhost:8080/api/info                           # note "banner"
# edit config-repo/product-service.yml -> change product.banner, save (no rebuild needed)
curl -s -X POST localhost:8081/actuator/refresh           # returns ["product.banner"]
curl -s localhost:8080/api/info                           # new banner, service never restarted
```

**6. Load balancing across two instances**
```bash
mvn -pl product-service spring-boot:run -Dspring-boot.run.arguments=--server.port=8083   # extra terminal
# wait ~30-60 s for registry caches to refresh, then:
for i in 1 2 3 4; do curl -s localhost:8080/api/info; echo; done                         # ports alternate 8081/8083
```
Each instance has its **own in-memory H2**, so their stock diverges. That is why real deployments use a shared database.

**7. Resilience: kill product-service**
```bash
# Ctrl+C the product-service terminal, then:
curl -s -i localhost:8080/api/orders/1/details     # 200: product = "unavailable" (read fallback degrades)
curl -s -i -X POST localhost:8080/api/orders -H 'Content-Type: application/json' -d '{"productId":1,"quantity":1}'   # 503 (write fallback fails explicitly)
```
Repeat the POST 5+ times: the circuit opens, so answers become instant (no more waiting on connection errors).
Restart product-service; after 10 s the breaker goes HALF_OPEN and closes again. Watch the order-service logs.

## 1-hour study plan

| Time | Do | Goal |
|---|---|---|
| 0:00–0:10 | Start everything, run experiments 1 and 2, open the Eureka dashboard | See the whole system alive |
| 0:10–0:20 | Read `config-repo/*.yml` + one service's `application.yml`; experiment 5 | Explain config import and refresh |
| 0:20–0:30 | Read `discovery-server/`; experiment 6 | Explain register / heartbeat / lookup / client-side LB |
| 0:30–0:42 | Read the product-service layers top to bottom; run `mvn test`; experiments 3-4 | Explain each Boot/JPA/validation piece |
| 0:42–0:52 | Read `ProductClient`, `ProductClientFallbackFactory`, `ResilienceConfig`; experiment 7 | Explain Feign + circuit breaker + why read/write fallbacks differ |
| 0:52–1:00 | Read `api-gateway.yml` + `RequestIdFilter`; answer the cheat-sheet questions out loud | Tell the architecture story without notes |

## Interview cheat sheet

**Config Server**
- *Why centralize config?* One source of truth per service/profile/environment, change without rebuilding, secrets encrypted (`{cipher}` values) or moved to Vault.
- *How does a client find it?* `spring.config.import=configserver:<url>` (Boot 2.4+). The old `bootstrap.yml` mechanism is legacy.
- *Precedence?* Command-line args beat Config Server values, which beat the local `application.yml`. Profile files beat the base file.
- *Refresh?* `POST /actuator/refresh` rebuilds `@RefreshScope` beans and rebinds `@ConfigurationProperties`. Doing it across many instances at once needs Spring Cloud Bus.
- *Trade-off?* The server is a boot-time dependency: run several instances and use `fail-fast` + retry.

**Eureka**
- Instances send a heartbeat every 30 s; if none arrives for 90 s the lease expires and the instance is evicted.
- Clients cache the registry (refresh every 30 s), so lookups are fast and survive a registry outage, but can be briefly stale.
- **Self-preservation**: if many heartbeats vanish at once, Eureka stops evicting (assumes a network problem). It is turned off here for dev only.
- Eureka favors availability over consistency (AP). Client-side discovery = the caller chooses the instance (vs a server-side load balancer).

**Feign / LoadBalancer**
- Feign turns an annotated interface into an HTTP client; the `name` is a service id resolved through Eureka.
- Two timeouts exist: Feign's read timeout (2 s) and the TimeLimiter (3 s). Keep the inner one shorter.
- `fallback` vs `fallbackFactory`: the factory receives the cause, so you can map 404/409/timeout differently.
- A `RequestInterceptor` bean adds headers to all Feign calls (auth token, caller name).

**Circuit breaker**
- CLOSED → OPEN when the failure rate passes the threshold (min calls first) → HALF_OPEN after the wait → CLOSED if probes succeed.
- Business errors (4xx) must not count as failures: `ignoreExceptions`. Otherwise "out of stock" could open the circuit.
- Read fallbacks may degrade; write fallbacks must fail loudly, or you invent data.
- Related patterns: retry (only for idempotent calls), bulkhead, rate limiter, timeout.

**API Gateway**
- Route = id + uri + predicates (match) + filters (modify). Global filters run for every route.
- Reactive (Netty) because a gateway is I/O-bound with many concurrent connections. Never mix in `spring-boot-starter-web`.
- Typical gateway duties: auth/JWT validation, rate limiting, CORS, request logging, response caching.

**Spring Boot core**
- Auto-configuration: `@EnableAutoConfiguration` loads classes listed in `AutoConfiguration.imports`, each guarded by `@Conditional*` (on class, on missing bean, on property).
- `@Transactional` works through a proxy: self-invocation (calling `this.method()`) bypasses it; `readOnly` is a hint; checked exceptions do not roll back by default.
- `spring.jpa.open-in-view=false` avoids hidden lazy loading in controllers and long-held connections.
- Slice tests (`@WebMvcTest`, `@DataJpaTest`) load only one layer, so they are fast; `@SpringBootTest` loads everything.
- Constructor injection: immutable dependencies, easy to unit-test, no reflection magic.

**Distributed-systems angle (senior questions)**
- No distributed transaction here: reserve succeeds, then the order insert may fail. Answer: saga with a compensating "release stock" call, or outbox + messaging.
- Retries need idempotency (idempotency key on `POST /orders`).
- Each service owns its data. `orders.productId` is a plain value, not a foreign key; consistency is eventual.

## Not included (natural next steps)

Spring Security + JWT at the gateway · Kafka/RabbitMQ events · Docker/Docker Compose (config server URL is already
overridable with `CONFIG_SERVER_URL`) · Micrometer Tracing + Zipkin · Flyway · Testcontainers (real Postgres) ·
Redis-backed caching and gateway rate limiting · Spring Cloud Bus for fan-out refresh.

## Troubleshooting

| Symptom | Cause |
|---|---|
| Service fails at boot with a config import error | Config server is not running yet (start order matters) |
| Config server returns empty properties | Working directory differs; set `CONFIG_REPO_LOCATION=file:/abs/path/to/config-repo/` |
| Gateway returns 503 for a route | Target not registered yet; wait ~30 s and check the Eureka dashboard |
| Feign call fails right after starting a new instance | Registry cache lag; retry after 30-60 s |
| Port already in use | Change it in `config-repo/<service>.yml` or pass `--server.port=` |
