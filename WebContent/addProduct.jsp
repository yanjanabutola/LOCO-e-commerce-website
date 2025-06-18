<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Product</title>
</head>
<body>
    <h2>Add New Product</h2>
    <form action="addProduct" method="post">
        <label>Product Name:</label><br>
        <input type="text" name="productName" required><br><br>

        <label>URL Slug:</label><br>
        <input type="text" name="urlSlug" required><br><br>

        <label>Description:</label><br>
        <textarea name="description" rows="3" cols="40"></textarea><br><br>

        <label>Category ID:</label><br>
        <input type="number" name="catId" required><br><br>

        <label>Price:</label><br>
        <input type="number" step="0.01" name="price" required><br><br>

        <label>Stock Quantity:</label><br>
        <input type="number" name="stockQuantity" required><br><br>

        <label>Status:</label><br>
        <select name="status">
            <option value="active" selected>Active</option>
            <option value="inactive">Inactive</option>
        </select><br><br>

        <button type="submit">Add Product</button>
    </form>
</body>
</html>
