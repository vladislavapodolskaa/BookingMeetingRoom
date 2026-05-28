Проект - Сервис для бронирования переговорных комнат. Разработан на базе Java Spring Boot и использует PostgreSQL в качестве базы данных.

Для запуска проекта требуется база данных.


Неоходимо в файле src/main/resourses/application.property вставить свои логин и пароль от базы данных. 


В корне проекта лежит файл docker-compose.yml с PostgreSQL последней версии.
Чтобы запустить базу данных в контейнере, выполните:

docker-compose up -d

Флаг -d запустит контейнер в фоновом режиме.

Ниже приведены примеры основных HTTP-запросов.
1. Создание нового бронирования

    Запрос: POST /api/bookings

    Заголовки (Headers): Content-Type: application/json

    Тело запроса (Body JSON):

JSON

{
    "userId": 2,
    "roomId":1,
    "bookingInterval": {
    "startTime": "2026-05-25T12:00:00",
    "endTime": "2026-05-25T13:00:00"
    },
    "topicOfMeeting":"Code review"
  }
}

    Пример успешного ответа (201 Created):

JSON

{
    "id": 6,
    "userId": 2,
    "roomId": 1,
    "bookingInterval": {
        "startTime": "2026-05-25T12:00:00",
        "endTime": "2026-05-25T13:00:00"
    },
    "status": "CONFIRMED",
    "topicOfMeeting": "Code review"
  }
}

2. Получение недельной статистики комнаты

После того как бронирование создано, оно автоматически учитывается в аналитике. Этот эндпоинт возвращает агрегированные данные по конкретной переговорной за последние 7 дней.

    Запрос: GET /room/1/statistic

    Пример ответа (200 OK):

JSON

{
  "roomName": "Большая переговорка",
  "totalBookings": 2,
  "activeDaysCount": 1,
  "averageMinutesPerDay": 180
}
