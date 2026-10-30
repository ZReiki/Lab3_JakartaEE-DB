<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="uk">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Watch Manager | JPA</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="container">
  <header class="page-header">
    <div class="logo">W</div>
    <div>
      <h1>Watch Manager</h1>
      <p>Керування базою даних годинників</p>
    </div>
  </header>

  <!-- Повідомлення про помилку, або успішно виконану операцію -->
  <c:if test="${not empty message}">
    <div class="message">${message}</div>
  </c:if>

  <!-- Додавання/редагування даних таблиці БД-->
  <section class="card">
    <h2>Дані годинника</h2>
    <p class="section-description">
      Заповніть поля для додавання або редагування запису.
    </p>

    <form method="post" action="${pageContext.request.contextPath}/watches">
      <input type="hidden" name="action" value="save">
      <input type="hidden" name="watchId" value="${watch.watchId}">

      <div class="form-grid">
        <div class="form-group">
          <label for="brand">Бренд</label>
          <input type="text" id="brand" name="brand" value="${watch.brand}" required placeholder="Наприклад, Casio">
        </div>

        <div class="form-group">
          <label for="type">Тип годинника</label>
          <select id="type" name="type">
            <option value="quartz">Кварцевий</option>
            <option value="mechanical">Механічний</option>
          </select>
        </div>

        <div class="form-group">
          <label for="releaseDate">Дата випуску</label>
          <input type="date" id="releaseDate" name="releaseDate" value="${watch.releaseDate}" required>
        </div>

        <div class="form-group">
          <label for="price">Ціна (грн)</label>
          <input type="number" id="price" name="price" value="${watch.price}" min="0" step="0.01" required>
        </div>

        <div class="form-group">
          <label for="caseDiameter">Діаметр корпусу (мм)</label>
          <input type="number" id="caseDiameter" name="caseDiameter" value="${watch.caseDiameter}" min="0" step="0.1" required>
        </div>

        <div class="form-group">
          <label for="quantity">Кількість</label>
          <input type="number" id="quantity" name="quantity" value="${watch.quantity}" min="0" required>
        </div>

        <div class="form-group">
          <label for="warrantyMonths">Гарантія (місяців)</label>
          <input type="number" id="warrantyMonths" name="warrantyMonths" value="${watch.warrantyMonths}" min="0" required>
        </div>

        <div class="form-group">
          <label for="manufacturerId">ID виробника</label>
          <input type="number" id="manufacturerId" name="manufacturerId" value="${watch.manufacturerId}" min="1" required>
        </div>
      </div>

      <div class="button-group">
        <button type="submit" class="btn btn-primary">Зберегти годинник</button>
        <a href="${pageContext.request.contextPath}/watches" class="btn btn-light">Очистити форму</a>
      </div>
    </form>
  </section>


  <section class="card">
    <h2>Пошук та операції</h2>

    <!-- findById, findAll-->
    <div class="operations">
      <form action="${pageContext.request.contextPath}/watches" method="get" class="search-form">
        <input type="hidden" name="action" value="find">

        <div class="form-group">
          <label for="searchId">ID годинника</label>
          <input type="number" id="searchId" name="id" min="1" required placeholder="Введіть ID">
        </div>

        <button type="submit" class="btn btn-primary">Знайти за ID</button>
      </form>

      <a href="${pageContext.request.contextPath}/watches" class="btn btn-light">Показати всі записи</a>
    </div>

    <!-- Delete -->
    <form action="${pageContext.request.contextPath}/watches" method="post" class="delete-form">
      <input type="hidden" name="action" value="delete">

      <div class="form-group">
        <label for="deleteId">ID для видалення</label>
        <input type="number" id="deleteId" name="id" min="1" required placeholder="Введіть ID">
      </div>

      <button type="submit" class="btn btn-danger">Видалити</button>
    </form>
  </section>


  <section class="card">
    <h2>Список годинників</h2>

    <div class="table-wrapper">
      <table>
        <thead>
        <tr>
          <th>ID</th>
          <th>Бренд</th>
          <th>Тип</th>
          <th>Дата випуску</th>
          <th>Ціна</th>
          <th>Діаметр</th>
          <th>Кількість</th>
          <th>Гарантія</th>
          <th>ID виробника</th>
          <th>Дії</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="item" items="${watches}">
          <tr>
            <td>${item.watchId}</td>
            <td>${item.brand}</td>
            <td>${item.type}</td>
            <td>${item.releaseDate}</td>
            <td>${item.price}</td>
            <td>${item.caseDiameter}</td>
            <td>${item.quantity}</td>
            <td>${item.warrantyMonths}</td>
            <td>${item.manufacturerId}</td>
            <td class="table-actions">
              <a class="btn btn-small btn-light" href="${pageContext.request.contextPath}/watches?action=edit&id=${item.watchId}">
                Редагувати
              </a>

              <form action="${pageContext.request.contextPath}/watches" method="post">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="id" value="${item.watchId}">
                <button type="submit" class="btn btn-small btn-danger">Видалити</button>
              </form>
            </td>
          </tr>
        </c:forEach>

        <c:if test="${empty watches}">
          <tr>
            <td colspan="10" class="empty-message">
              Записів поки немає або пошук не дав результатів.
            </td>
          </tr>
        </c:if>
        </tbody>
      </table>
    </div>
  </section>

  <footer>
    Лабораторна робота №3 · Java Persistence API
  </footer>
</div>

</body>
</html>
