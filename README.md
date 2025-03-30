1. CreateProduct Event
----------------------------------------------------
curl --location 'http://localhost:8081/products' \
--header 'Content-Type: application/json' \
--data '{
    "type": "CreateProduct",
    "product": {
        "name": "Books",
        "description": "KK publication",
        "price": 999
    }
}'
2. UpdateProduct Event
------------------------------------------------------------------
curl --location --request PUT 'http://localhost:8081/products/1' \
--header 'Content-Type: application/json' \
--data '{
    "type": "UpdateProduct",
    "product": {
        "id": 700f01fc-c313-4a12-a522-57700adabdfa,
        "name": "Watch",
        "description": "Samsung latest model",
        "price": 58000.0
    }
}'
