# Модель прецедентів 🗂️

## 📋 Загальна схема

@startuml

actor User as U
actor Manager as M
actor Administrator as A

M -r-|> U
A -r-|> M

U -d-> (Create User)
U -d-> (Authorize User)
U -d-> (Edit User)
U -u-> (Create Project)
U -u-> (Edit Project)
U -u-> (Delete Project)

M -d-> (Add User To Project)
M -d-> (Remove User From Project)
M -u-> (Create Board)
M -u-> (Delete Board)

A -d-> (Delete User)
A -d-> (Block Project)
A -d-> (Unblock Project)
A -u-> (Ban User)
A -u-> (Unban User)
A -u-> (Edit System Settings)

@enduml

## 🧑‍💻 Користувач

@startuml

actor User as U

U -d-> (Create User)
U -d-> (Authorize User)
U -d-> (Edit User)
U -u-> (Create Project)
U -u-> (Edit Project)
U -u-> (Delete Project)

@enduml

## 🛠️ Адміністратор

@startuml

actor Administrator as A

A -d-> (Delete User)
A -d-> (Edit User)
A -u-> (Edit Project)
A -u-> (Delete Project)
A -u-> (Add User To Project)
A -r-> (Block Project)
A -u-> (Unblock Project)
A -d-> (Ban User)
A -d-> (Unban User)
A -l-> (Edit System Settings)

@enduml

## 📊 Керівник

@startuml

actor Manager as M

M -d-> (Create Project)
M -d-> (Edit Project)
M -d-> (Delete Project)
M -r-> (Add User To Project)
M -l-> (Remove User From Project)
M -u-> (Create Board)
M -u-> (Delete Board)

@enduml

<br>

# Сценарії використання 💡

## 👤 CreateUser

@startuml

actor User
control System

User -> System: Click "Register"
User -> System: Fill registration form (username, email, password)
User -> System: Click "Create"
System -> System: Validate input
System -> System: Create user account
System -> User: Automatically log in

@enduml

| **ID**           | CreateUser |
|------------------|------------|
| **НАЗВА**        | Створити користувача |
| **УЧАСНИКИ**     | Користувач, система |
| **ПЕРЕДУМОВИ**   | Система не зареєструвала користувача |
| **РЕЗУЛЬТАТ**    | Система створює обліковий запис користувача |
| **ВИКЛЮЧНІ СИТУАЦІЇ** | - Користувач не ввів ім'я користувача (**NullUsernameException**) <br> - Користувач не ввів пошту (**NullEmailException**) <br> - Користувач не ввів пароль (**NullPasswordException**) <br> - Користувач з таким ім'ям вже існує (**UserAlreadyExistsException**) <br> - Користувач вказав неправильний формат пошти (**WrongEmailFormatException**) <br> - Користувач ввів недостатньо сильний пароль (**WeakPasswordException**) |
| **ОСНОВНИЙ СЦЕНАРІЙ** | 1. Користувач натискає на кнопку "Зареєструватись". <br> 2. Користувач заповнює поля реєстрації (ім'я користувача, пошта, пароль). <br> 3. Користувач натискає на кнопку "Створити". <br> 4. Система перевіряє введені поля на валідність. <br> 5. Система створює обліковий запис користувача. <br> 6. Користувач автоматично входить у систему. |

## 👤 AuthorizeUser

@startuml

actor User
control System

User -> System: Input username and password
System -> System: Check username and password (InvalidPasswordException, InvalidUsernameException)
System -> System: Check user status (UserBannedException)
System -> User: Successfully logged in

@enduml

| **ID**           | AuthorizeUser |
|------------------|--------------|
| **НАЗВА**        | Авторизувати користувача |
| **УЧАСНИКИ**     | Користувач, система |
| **ПЕРЕДУМОВИ**   | Система зареєструвала користувача |
| **РЕЗУЛЬТАТ**    | Система авторизувала користувача |
| **ВИКЛЮЧНІ СИТУАЦІЇ** | - Користувач ввів неправильний пароль (**InvalidPasswordException**) <br> - Користувач ввів неправильне ім'я користувача (**InvalidUsernameException**) <br> - Система заблокувала користувача (**UserBannedException**) |
| **ОСНОВНИЙ СЦЕНАРІЙ** | 1. Користувач вводить ім'я користувача і пароль. <br> 2. Система перевіряє введені дані (**InvalidPasswordException** або **InvalidUsernameException**). <br> 3. Система перевіряє статус користувача (**UserBannedException**). <br> 4. Користувач успішно входить у систему. |

## 👤 EditUser

@startuml

actor Admin as A
control System as S

A -> S: Open user profile
A -> S: Edit user fields
S -> S: Check permissions (InsufficientPermissionsException)
S -> S: Validate data (InvalidDataFormatException)
S -> S: Save updated user data
S -> A: Successfully edited user

@enduml

| **ID**           | EditUser |
|------------------|------------|
| **НАЗВА**        | Редагувати користувача |
| **УЧАСНИКИ**     | Користувач, адміністратор, система |
| **ПЕРЕДУМОВИ**   | Система авторизувала користувача або адміністратора |
| **РЕЗУЛЬТАТ**    | Система змінила дані користувача |
| **ВИКЛЮЧНІ СИТУАЦІЇ** | - Система не знайшла користувача (**UserNotFoundException**) <br> - Користувач має недостатньо прав для редагування (**InsufficientPermissionsException**) <br> - Користувач ввів дані у неправильному форматі (**InvalidDataFormatException**) |
| **ОСНОВНИЙ СЦЕНАРІЙ** | 1. Адміністратор або користувач відкриває профіль користувача. <br> 2. Користувач або адміністратор змінює потрібні поля. <br> 3. Система перевіряє права (**InsufficientPermissionsException**). <br> 4. Система перевіряє введені дані на правильність (**InvalidDataFormatException**). <br> 5. Система зберігає оновлені дані користувача. |

## 👤 DeleteUser

@startuml

actor Admin
control System

Admin -> System: Select user to delete
Admin -> System: Click "Delete User"
System -> System: Check permissions (InsufficientPermissionsException)
System -> System: Delete user
System -> Admin: User deleted

@enduml

| **ID**           | DeleteUser |
|------------------|------------|
| **НАЗВА**        | Видалити користувача |
| **УЧАСНИКИ**     | Адміністратор, система |
| **ПЕРЕДУМОВИ**   | Система авторизувала адміністратора |
| **РЕЗУЛЬТАТ**    | Система видаляє користувача |
| **ВИКЛЮЧНІ СИТУАЦІЇ** | - Система не знайшла користувача (**UserNotFoundException**) <br> - Користувач має недостатньо прав для видалення (**InsufficientPermissionsException**) |
| **ОСНОВНИЙ СЦЕНАРІЙ** | 1. Адміністратор вибирає користувача для видалення. <br> 2. Адміністратор натискає кнопку "Видалити користувача". <br> 3. Система перевіряє права адміністратора (**InsufficientPermissionsException**). <br> 4. Система видаляє користувача (**UserNotFoundException**). |

## 📂 CreateProject

@startuml

actor User
control System

User -> System: Click "Create Project"
User -> System: Fill project form (project name)
System -> System: Validate input (NullProjectNameException, InvalidProjectNameException)
System -> System: Create project
System -> User: Assign project manager rights
System -> User: Confirmation message

@enduml

| **ID**           | CreateProject |
|------------------|------------|
| **НАЗВА**        | Створити проект |
| **УЧАСНИКИ**     | Користувач, система |
| **ПЕРЕДУМОВИ**   | Система авторизувала користувача |
| **РЕЗУЛЬТАТ**    | Система створює проєкт та надає права керівника проєкту користувачу |
| **ВИКЛЮЧНІ СИТУАЦІЇ** | - Користувач не ввів назву проєкту (**NullProjectNameException**) <br> - Користувач ввів назву проєкту у неправильному форматі (**InvalidProjectNameException**) |
| **ОСНОВНИЙ СЦЕНАРІЙ** | 1. Користувач натискає кнопку "Створити проект". <br> 2. Користувач заповнює форму (назва проекту). <br> 3. Система перевіряє дані на валідність. <br> 4. Система створює новий проект. <br> 5. Система надає права керівника проєкту користувачу. <br> 6. Користувач отримує підтвердження про створення проекту. |

## 📂 EditProject

@startuml

actor User
control System

User -> System: Open project
User -> System: Click "Edit"
User -> System: Modify project details
System -> System: Check permissions (AccessDeniedException)
System -> System: Save changes
System -> User: Successfully edited project

@enduml

| **ID**            | EditProject |
|------------------|-----------------|
| **НАЗВА**         | Редагувати проект |
| **УЧАСНИКИ**     | Користувач (керівник проєкту), адміністратор, система |
| **ПЕРЕДУМОВИ**   | - Система авторизувала користувача <br> - Користувач має права на редагування проекту |
| **РЕЗУЛЬТАТ**    | Система змінює дані проєкту |
| **ВИКЛЮЧНІ СИТУАЦІЇ** | - Система не знайшла проєкт (**ProjectNotFoundException**) <br> - Користувач має недостатньо прав для редагування (**AccessDeniedException**) |
| **ОСНОВНИЙ СЦЕНАРІЙ** | 1. Користувач відкриває проект. <br> 2. Користувач натискає кнопку "Редагувати". <br> 3. Користувач вносить зміни. <br> 4. Система перевіряє права на редагування. <br> 5. Система зберігає зміни. |

## 📂 DeleteProject

@startuml

actor User
control System

User -> System: Select project to delete
User -> System: Click "Delete Project"
System -> System: Check permissions (AccessDeniedException)
System -> System: Delete project
System -> User: Successfully deleted project

@enduml

| **ID**            | DeleteProject |
|------------------|-----------------|
| **НАЗВА**         | Видалити проект |
| **УЧАСНИКИ**     | Користувач (керівник проєкту), адміністратор, система |
| **ПЕРЕДУМОВИ**   | - Система авторизувала користувача <br> - Користувач має права на видалення проєкту |
| **РЕЗУЛЬТАТ**    | Система видаляє проєкт |
| **ВИКЛЮЧНІ СИТУАЦІЇ** | - Система не знайшла проєкт (**ProjectNotFoundException**) <br> - Користувач має недостатньо прав для видалення (**AccessDeniedException**) |
| **ОСНОВНИЙ СЦЕНАРІЙ** | 1. Користувач вибирає проект для видалення. <br> 2. Користувач натискає кнопку "Видалити". <br> 3. Система перевіряє права на видалення. <br> 4. Система видаляє проект. |

## ➕ AddUserToProject

@startuml

actor User
control System

User -> System: Open project
User -> System: Click "Add Participant"
User -> System: Input participant details
System -> System: Check permissions (AccessDeniedException)
System -> System: Add participant to project
System -> User: Participant added

@enduml

| **ID**            | AddUserToProject |
|------------------|-----------------|
| **НАЗВА**         | Додати учасника до проекту |
| **УЧАСНИКИ**     | Користувач (керівник проєкту), адміністратор, система |
| **ПЕРЕДУМОВИ**   | - Система авторизувала користувача <br> - Користувач має права на редагування проекту |
| **РЕЗУЛЬТАТ**    | Система додає учасника до проєкту |
| **ВИКЛЮЧНІ СИТУАЦІЇ** | - Система не знайшла користувача (**UserNotFoundException**) <br> - Система не знайшла проєкт (**ProjectNotFoundException**) <br> - Користувач має недостатньо прав для додавання учасника (**AccessDeniedException**) |
| **ОСНОВНИЙ СЦЕНАРІЙ** | 1. Користувач відкриває проект. <br> 2. Користувач натискає кнопку "Додати учасника". <br> 3. Користувач вводить дані нового учасника. <br> 4. Система перевіряє права на додавання учасника. <br> 5. Система додає учасника до проекту. |

## 🗑️ RemoveUserFromProject

@startuml

actor Manager
control System

Manager -> System: Open project
Manager -> System: Click "Remove User"
System -> System: Open remove user form
Manager -> System: Input username
System -> System: Validate input (RemoveUserFromProject_WrongUsername_EXC)
Manager -> System: Click "Remove"
System -> System: Remove user from project
System -> Manager: User removed

@enduml

| **ID**             | RemoveUserFromProject |
|--------------------|----------------------|
| **НАЗВА**         | Видалити користувача з проєкту |
| **УЧАСНИКИ**      | Менеджер, система |
| **ПЕРЕДУМОВИ**    | - Користувач є учасником проєкту |
| **РЕЗУЛЬТАТ**     | Видалений член проєкту |
| **ВИКЛЮЧНІ СИТУАЦІЇ** | - **RemoveUserFromProject_WrongUsername_EXC** – менеджер ввів неправильне ім'я користувача <br> - **RemoveUserFromProject_CancelButton_EXC** – менеджер натиснув кнопку "Відміна" |
| **ОСНОВНИЙ СЦЕНАРІЙ** | 1. Менеджер переходить у розділ "Проєкти". <br> 2. Менеджер обирає проєкт і натискає кнопку "Видалити користувача". <br> 3. Система відкриває форму для введення ім'я користувача. <br> 4. Менеджер вводить ім'я користувача (**можлива RemoveUserFromProject_WrongUsername_EXC**). <br> 5. Менеджер натискає кнопку "Видалити" (**можлива RemoveUserFromProject_CancelButton_EXC**). <br> 6. Система видаляє користувача з проєкту. <br> 7. Система закриває форму. <br> 8. Система показує повідомлення, що користувач успішно видалений з обраного проєкту. |

## 📝 CreateBoard

@startuml

actor Manager
control System

Manager -> System: Click "Create Board"
System -> System: Open board creation form
Manager -> System: Fill board form
Manager -> System: Click "Create"
System -> System: Validate input (CreateBoard_NoName_EXC, CreateBoard_ExistingName_EXC)
System -> System: Create board
System -> Manager: Confirmation message

@enduml

| **ID**             | CreateBoard |
|--------------------|------------|
| **НАЗВА**         | Створити дошку |
| **УЧАСНИКИ**      | Менеджер, система |
| **ПЕРЕДУМОВИ**    | - Менеджер авторизований <br> - Менеджер є членом проєкту |
| **РЕЗУЛЬТАТ**     | Нова дошка у проєкті |
| **ВИКЛЮЧНІ СИТУАЦІЇ** | - **CreateBoard_NoName_EXC** – менеджер не вказав назву дошки <br> - **CreateBoard_ExistingName_EXC** – менеджер ввів ім'я дошки, що вже зайнято <br> - **CreateBoard_CancelButton_EXC** – менеджер натиснув кнопку "Відміна" |
| **ОСНОВНИЙ СЦЕНАРІЙ** | 1. Менеджер обирає проєкт і натискає на кнопку "Створити дошку". <br> 2. Система відкриває форму із полями інформації про дошку (**можлива CreateBoard_CancelButton_EXC**). <br> 3. Менеджер заповнює інформацію про дошку: вказує назву та опис. <br> 4. Менеджер натискає кнопку "Створити". <br> 5. Система перевіряє на валідність інформацію про дошку (**можливі CreateBoard_NoName_EXC та CreateBoard_ExistingName_EXC**). <br> 6. Система створює нову дошку у проєкті. |

## 📝 DeleteBoard

@startuml

actor Manager
control System

Manager -> System: Select board to delete
Manager -> System: Click "Delete Board"
System -> System: Check permissions (DeleteBoard_NoRights_EXC)
Manager -> System: Input board name for confirmation
System -> System: Validate input (DeleteBoard_InvalidName_EXC)
System -> System: Delete board
System -> Manager: Board deleted

@enduml

| **ID**             | DeleteBoard |
|--------------------|------------|
| **НАЗВА**         | Видалити дошку |
| **УЧАСНИКИ**      | Менеджер, система |
| **ПЕРЕДУМОВИ**    | - Менеджер має дошку у проєкті <br> - Система містить інформацію про дошку для видалення |
| **РЕЗУЛЬТАТ**     | Видалена дошка |
| **ВИКЛЮЧНІ СИТУАЦІЇ** | - **DeleteBoard_NoRights_EXC** – менеджер не має прав на видалення обраної дошки <br> - **DeleteBoard_InvalidName_EXC** – менеджер вказав ім'я дошки, що не збігається з реальним <br> - **DeleteBoard_CancelButton_EXC** – менеджер натиснув кнопку "Відміна" |
| **ОСНОВНИЙ СЦЕНАРІЙ** | 1. Менеджер переходить у розділ "Дошки" та обирає потрібну для видалення дошку. <br> 2. Менеджер натискає кнопку "Видалити дошку". <br> 3. Система перевіряє права менеджера на видалення обраної дошки (**можлива DeleteBoard_NoRights_EXC**). <br> 4. Система відкриває форму підтвердження видалення дошки. <br> 5. Менеджер вводить назву дошки для підтвердження процесу видалення (**можлива DeleteBoard_InvalidName_EXC**). <br> 6. Менеджер натискає кнопку "Видалити дошку" (**можлива DeleteBoard_CancelButton_EXC**). <br> 7. Система видаляє дошку з проєкту. |

## ⛔ BlockProject

@startuml

actor Admin
control System

Admin -> System: Select project to block
Admin -> System: Click "Block Project"
System -> System: Open block project form
Admin -> System: Fill block reason and duration
Admin -> System: Click "Confirm"
System -> System: Validate project (BlockProject_ProjectHasBeenRemoved_EXC, BlockProject_ProjectHasBeenBlocked_EXC)
System -> System: Block project
System -> Admin: Project blocked

@enduml

| **ID**             | BlockProject |
|--------------------|-------------|
| **НАЗВА**         | Заблокувати проєкт |
| **УЧАСНИКИ**      | Адміністратор, система |
| **ПЕРЕДУМОВИ**    | - Адміністратор авторизований <br> - Система містить дані про проєкт <br> - Проєкт порушує умови використання системи |
| **РЕЗУЛЬТАТ**     | Заблокований проєкт |
| **ВИКЛЮЧНІ СИТУАЦІЇ** | - **BlockProject_ProjectHasBeenRemoved_EXC** – проєкт видалено з системи <br> - **BlockProject_ProjectHasBeenBlocked_EXC** – проєкт вже заблоковано <br> - **BlockProject_CancelButton_EXC** – адміністратор натиснув кнопку "Відміна" |
| **ОСНОВНИЙ СЦЕНАРІЙ** | 1. Адміністратор переходить у розділ "Проєкти" та вибирає потрібний для блокування проєкт. <br> 2. Адміністратор натискає кнопку "Заблокувати проєкт". <br> 3. Система відкриває форму із параметрами блокування проєкту. <br> 4. Адміністратор заповнює форму, вказуючи причину та термін дії блокування. <br> 5. Адміністратор натискає кнопку "Підтвердити" (**можлива BlockProject_CancelButton_EXC**). <br> 6. Система перевіряє валідність обраного адміністратором проєкту (**можливі BlockProject_ProjectHasBeenRemoved_EXC, BlockProject_ProjectHasBeenBlocked_EXC**). <br> 7. Система здійснює операцію блокування й повідомляє менеджера цього проєкту та адміністратора про заблокований проєкт. |

## ✅ UnblockProject

@startuml

actor Admin
control System

Admin -> System: Select blocked project
Admin -> System: Click "Unblock Project"
Admin -> System: Click "Confirm"
System -> System: Validate project
System -> System: Unblock project
System -> Admin: Project unblocked

@enduml

| **ID**             | UnblockProject |
|--------------------|----------------|
| **НАЗВА**         | Розблокувати проєкт |
| **УЧАСНИКИ**      | Адміністратор, система |
| **ПЕРЕДУМОВИ**    | - Адміністратор авторизований <br> - Проєкт заблокований в системі |
| **РЕЗУЛЬТАТ**     | Розблокований проєкт |
| **ВИКЛЮЧНІ СИТУАЦІЇ** | - **UnblockProject_ProjectHasBeenRemoved_EXC** – проєкт видалено з системи <br> - **UnblockProject_ProjectHasBeenUnblocked_EXC** – проєкт вже розблоковано <br> - **UnblockProject_CancelButton_EXC** – адміністратор натиснув кнопку "Відміна" |
| **ОСНОВНИЙ СЦЕНАРІЙ** | 1. Адміністратор переходить у розділ "Заблоковані проєкти" та вибирає потрібний для розблокування проєкт. <br> 2. Адміністратор натискає на кнопку "Розблокувати проєкт". <br> 3. Адміністратор натискає кнопку "Підтвердити" (**можлива UnblockProject_CancelButton_EXC**). <br> 4. Система перевіряє валідність обраного адміністратором проєкту (**можливі UnblockProject_ProjectHasBeenRemoved_EXC, UnblockProject_ProjectHasBeenUnblocked_EXC**). <br> 5. Система здійснює операцію розблокування й повідомляє менеджера цього проєкту та адміністратора про успішно розблокований проєкт. |

## 🚫 BanUser

@startuml

actor Administrator
control System

Administrator -> System: Detect suspicious user activity
Administrator -> System: Fill in the ban form\n(reason and ban duration)
Administrator -> System: Click "Confirm"
System -> System: Validate user data
System -> System: Ban user
System -> Administrator: Confirm user has been banned

@enduml

@startuml

actor User
control System

System -> User: Notify about the ban

@enduml

| **ID**             | BanUser |
|--------------------|---------|
| **НАЗВА**         | Заблокувати користувача |
| **УЧАСНИКИ**      | Адміністратор, система |
| **ПЕРЕДУМОВИ**    | - Користувач багаторазово неправильно вводить пароль <br> - Адміністратор виявив підозрілу активність користувача <br> - Користувач порушує умови використання системи |
| **РЕЗУЛЬТАТ**     | Заблокований користувач |
| **ВИКЛЮЧНІ СИТУАЦІЇ** | - **BanUser_NoMatchingUser_EXC** – введені дані не відповідають жодному користувачеві <br> - **BanUser_UserHasBeenRemoved_EXC** – користувача видалено з системи <br> - **BanUser_UserHasBeenBanned_EXC** – користувача вже заблоковано <br> - **BanUser_CancelButton_EXC** – адміністратор натиснув кнопку "Відміна" |
| **ОСНОВНИЙ СЦЕНАРІЙ** | 1. Адміністратор фіксує підозрілу активність користувача. <br> 2. Адміністратор заповнює спеціальну форму для блокування, вказуючи причину та термін дії блокування. <br> 3. Адміністратор натискає кнопку "Підтвердити" (**можлива BanUser_CancelButton_EXC**). <br> 4. Система перевіряє валідність введених адміністратором даних (**можливі BanUser_NoMatchingUser_EXC, BanUser_UserHasBeenRemoved_EXC, BanUser_UserHasBeenBanned_EXC**). <br> 5. Система виконує блокування користувача і повідомляє його про це. |

## 👍 UnbanUser

@startuml

actor Administrator
control System

Administrator -> System: Select blocked user
Administrator -> System: Click "Unban User"
Administrator -> System: Click "Confirm"
System -> System: Validate user data
System -> System: Unban user
System -> Administrator: Confirm user has been unbanned

@enduml

@startuml

actor User
control System

System -> User: Notify about unban

@enduml

| **ID**             | UnbanUser |
|--------------------|----------|
| **НАЗВА**         | Розблокувати користувача |
| **УЧАСНИКИ**      | Адміністратор, система |
| **ПЕРЕДУМОВИ**    | - Користувач заблокований |
| **РЕЗУЛЬТАТ**     | Розблокований користувач |
| **ВИКЛЮЧНІ СИТУАЦІЇ** | - **UnbanUser_NoMatchingUser_EXC** – введені дані не відповідають жодному користувачеві <br> - **UnbanUser_UserHasBeenRemoved_EXC** – користувача видалено з системи <br> - **UnbanUser_UserHasBeenUnbanned_EXC** – користувача вже розблоковано <br> - **UnbanUser_CancelButton_EXC** – адміністратор натиснув кнопку "Відміна" |
| **ОСНОВНИЙ СЦЕНАРІЙ** | 1. Адміністратор фіксує потрібного користувача. <br> 2. Адміністратор натискає на кнопку "Розблокувати користувача". <br> 3. Адміністратор натискає кнопку "Підтвердити" (**можлива UnbanUser_CancelButton_EXC**). <br> 4. Система перевіряє валідність введених адміністратором даних (**можливі UnbanUser_NoMatchingUser_EXC, UnbanUser_UserHasBeenRemoved_EXC, UnbanUser_UserHasBeenUnbanned_EXC**). <br> 5. Система виконує розблокування користувача і повідомляє його про це. |

## ⚙️ EditSystemSettings

@startuml

actor Administrator
control System

Administrator -> System: Log in to the system
Administrator -> System: Select "Edit System Settings"
System -> System: Open system settings edit form
Administrator -> System: Modify system settings
Administrator -> System: Click "Save Changes"
System -> System: Save updated settings
System -> Administrator: Confirm settings saved

@enduml

| **ID**             | EditSystemSettings |
|--------------------|-------------------|
| **НАЗВА**         | Редагувати налаштування системи |
| **УЧАСНИКИ**      | Адміністратор, система |
| **ПЕРЕДУМОВИ**    | - Адміністратор авторизований |
| **РЕЗУЛЬТАТ**     | Нові налаштування системи |
| **ВИКЛЮЧНІ СИТУАЦІЇ** | - **EditSystemSettings_InvalidData_EXC** – адміністратор ввів невалідні дані <br> - **EditSystemSettings_CancelButton_EXC** – адміністратор натиснув кнопку "Відміна" |
| **ОСНОВНИЙ СЦЕНАРІЙ** | 1. Адміністратор входить в систему. <br> 2. Адміністратор обирає опцію "Редагувати налаштування системи". <br> 3. Система відкриває форму зміни налаштувань системи (**можлива EditSystemSettings_CancelButton_EXC**). <br> 4. Адміністратор змінює налаштування системи (**можлива EditSystemSettings_InvalidData_EXC**). <br> 5. Адміністратор натискає кнопку "Зберегти зміни". <br> 6. Система зберігає змінені налаштування. |