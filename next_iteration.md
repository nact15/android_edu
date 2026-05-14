1. retrofit (первый запрос в сеть с пагинацией)
    - сетевой клиент - OkHttp3
    - инспектор запросов - Chucker
    - Logging interceptor
    - Token interceptor
    - Auth interceptor
    - Конвертер фактори - kotlinx serialization json
    - Колл адаптер фактори - coroutines
2. Авторизация - экран логика (токены хранить в DataStore (androidx - https://habr.com/ru/articles/874034/) но так же глянуть
   SharedPreferences)
3. appbar - заголовок для страниц нижней навигации
4. SwipeRefreshLayout
5. recycler view (grid layout manager) - сделать грид элементов в 2 колонки в вертикальной ориентации, 4 колонки в горизонтальной
6. mutex (сделать isLoading через mutex)
7. Glide - для загрузки фото
8. Экран детального представления айтема