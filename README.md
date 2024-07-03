## Требования тз.

## Сервер

- [✔️] Запуск вебсокета.
- [✔️] Результат сканирования — скан, то есть, дерево папок и файлов. Для каждого файла фиксируется его размер. .
- [✔️] В дереве новые файлы и папки, по сравнению с прошлым сканированием, имеют статус "Зелёный", а файлы, размер которых изменился по сравнению с прошлым сканированием, имеют статус "Синий"..
- [✔️] в скане хранятся ещё три мета-значения: размер всех файлов, время сканирования в миллисекундах, дата-время начала сканирования. 
- [✔️] Если обнаружилось какое-либо изменение по сравнению с прошлым сканом, то:
- Вся файловая структура архивируется. 
- Текущий скан записывается на сервере в локальную базу SQL lite: само дерево и мета-значения сохраняются в виде txt-файла, а в базу записывается адрес  txt-файла и адрес архива. 
- [✔️] Каждый скан сервер отправляет клиенту.
- [✔️] Сервер каждые 100мс отслеживает объем памяти М1, которую использует процесс, а так же максимальный объем памяти Мmax, предусмотренный ОС для этого процесса. Эти значения также отправляются клиенту в формате M1/Mmax.
- [✔️] Сервер и клиент общаются по протоколу websocket. Для сетевого взаимодействия использовать Ktor.
- [✔️] UI на Compose: кнопка Config для выбора порта, кнопка Включить для запуска сервера, кнопка Выключить для выключения сервера. 
## Клиент
- [✔️] Клиент в онлайн режиме показывает M1/Mmax и графическую визуализацию скана (дерева), включая цвета статусов и мета-значения.
- [❌] Если на клиенте нажать Стоп, то процесс сканирования на сервере останавливается. Нажать Старт - продолжится.
- [✔️] При этом проверка и отправка M1/Mmax не останавливается никогда, если сервер включён.
- [✔️] Если на клиенте нажать Scan List, то откроется список сканов. У каждого скана есть кнопка Восстановить.
- [❌] При нажатии клиент отправляет серверу id скана, в свою очередь сервер: завершает процесс хрома, заменяет текущую файловую структуру хрома на файловую структуру скана (которая хранится в архиве), запускает хром, сообщает клиенту об успешном восстановлении. На сервере отображается "Скан id восстановлен, дата-время восстановления,  время потраченное сервером на восстановление".

## Серверная часть
<img src="/assets/server_serverarch.png" width="1080">

| Модуль | Описание |
| ------------- | ------------- |
| **:server:domain** | • **manager** - интерфейсы для работы с памятью, архивацией, сканированием.<br> • **interactor** - интерфейс, отвечающий за управление сервером.<br> • **repository** - интерфейс для работы с данными. |
| **:server:data:repository** | • **Impl** - реализация репозитория.<br> • **datasource** - абстракция источника данных.<br> • **модели** - свои модели данных. |
| **:server:data:local** | • **модели данных** - свои модели данных.<br> • **Impl datasource** - реализация источника данных.<br> • **dao** - объекты доступа к данным.<br> • **Room db инициализация** - инициализация базы данных Room. |
| **:server:data:archiving** | • **ArchiveManager** - реализация менеджера архивации. |
| **:data:scaning** | • **ScanManager** - реализация менеджера сканирования. |
| **:server:data:memory** | • **MemoryManager** - реализация менеджера памяти. |




## Клиентская часть

| Модуль | Описание |
| ------------- | ------------- |
| **:client:domain** | • **repository** - общий репозиторий. |
| **:client:data:repository** | • **Impl** - реализация репозитория.<br> • **datasource** - абстракция источника данных.<br> • **модели** - свои модели данных. |
| **:client:data:network** | • **модели данных** - свои модели данных.<br> • **Impl datasource** - реализация источника данных.<br> • **Подключение к соккету** - реализация подключения к WebSocket. |
| **:client:features:config** | • **Экран для подключения** - экран для подключения, отслеживания данных с сервера.<br> • **viewmodel** - модель представления.<br> • **модели** - свои модели данных. |
| **:client:features:scanlist** | • **Экран со всеми сканами** - экран, отображающий все сканы. |
<img src="/assets/client_arch.png" width="1080"/>

##Непонятные моменты из тз.
Если обнаружилось какое-либо изменение по сравнению с прошлым сканом, то:
- Вся файловая структура архивируется. 
- Текущий скан записывается на сервере в локальную базу SQL lite: само дерево и мета-значения сохраняются в виде txt-файла, а в базу записывается адрес  txt-файла и адрес архива. 
Должен ли находиться один архив, который перезаписывается при изменении?
Нужна ли бд в клиенте или для тз пойдет и in-memory вариант для просмотра ScanList




## Чтобы изменил
Добавить Result что бы не использовать try/catch
WebSocketWrapper что бы удобнее отправлять/слушать сообщения 
Shared модуль
Добавить PermissionCheck, вместо захардкоженной кнопки



