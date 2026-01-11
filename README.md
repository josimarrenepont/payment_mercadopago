# Payment Service API

API de pagamento integrada ao Mercado Pago.

## 🔹 Como funciona

- Endpoint: `/api/v1/payments/checkout`
- Em **dev**, retorna um `init_point` fake:
https://sandbox.mercadopago.com.br/checkout/v1/redirect?pref_id=MOCK

markdown
Copiar código
- Em **prod**, usa o gateway real e precisa de token válido.

## 🔹 Profiles Spring

- `dev` → MockMercadoPagoClient
- `prod` → MercadoPagoClient (token real necessário)

## 🔹 Rodando localmente

```bash
# dev (mock)
java -jar payment.jar --spring.profiles.active=dev
```
## 🔹 Rodando em produção
```bash
# prod (real)
java -jar payment.jar --spring.profiles.active=prod
```

🔹 Docker
```bash
docker-compose up
```

⚠️ Integração real depende de credenciais e habilitação da conta Mercado Pago.
