### Описание проекта

Этот проект представляет собой Rest API сервис для работы с условной сущностью Product. Сервис реализован на основе Spring Boot и поддерживает CRUD операции, а также позволяет сравнить производительность JDBC и JPA.

### Технологии, использованные в проекте:
- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **JDBC Template**
- **Liquibase** (для миграций БД)
- **H2 Database** (для тестирования)
- **WebTestClient** (для тестирования REST API)

### Основные Endpoints

1. **Создание продукта**:
   - **URL**: `POST /api/product/save`
   - **Описание**: Создает новый продукт.
   - **Пример запроса**:
     ```json
     {
       "name": "Product 1"
     }
     ```

2. **Обновление продукта**:
   - **URL**: `PATCH /api/product/update`
   - **Описание**: Обновляет существующий продукт.
   - **Пример запроса**:
     ```json
     {
       "id": 1,
       "name": "Updated Product"
     }
     ```

3. **Получение продукта по ID**:
   - **URL**: `GET /api/product/find-by-id/{id}`
   - **Описание**: Возвращает продукт по его ID.

4. **Удаление продукта**:
   - **URL**: `DELETE /api/product/delete-by-id/{id}`
   - **Описание**: Удаляет продукт по его ID.

5. **Тестирование производительности**:
   - **JPA**:
     - **URL**: `POST /api/product/save-all`
   - **JDBC**:
     - **URL**: `POST /api/product/jdbc/save-all`

### Запуск проекта

1. Клонируйте репозиторий:
   ```bash
   git clone <repository_url>
   ```

2. Убедитесь, что у вас установлена Java 11 и Maven.

3. Запустите проект с использованием команды:
   ```bash
   mvn spring-boot:run
   ```

4. Для запуска тестов используйте команду:
   ```bash
   mvn test
   ```

### Тестирование

Проект содержит тесты производительности, которые можно запустить для сравнения скорости работы с базой данных через JPA и JDBC.

- Для JPA: `ProductPerformanceTest#testPerformanceJpa()`
- Для JDBC: `ProductPerformanceTest#testPerformanceJdbc()`

### Миграции базы данных

Для миграций используется **Liquibase**. Скрипты миграций находятся в папке `resources/db/changelog`.

