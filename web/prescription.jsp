<%-- 
    Document   : prescription
    Created on : Jun 10, 2025, 7:44:40 AM
    Author     : An
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Kê Toa Thuốc - Phòng Khám Răng</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/prescription.css">       
    </head>
    <body>
        <div class="container">
            <h2>Tạo Toa Thuốc - Phòng Khám Răng</h2>
            <form method="post" action="CreateMedicalRecordServlet">
                <div>
                    <h3>Thông Tin Bệnh Nhân</h3>
                    <table border="0">
                        <tbody>
                            <tr>
                                <td><label>Họ tên bệnh nhân:</label>
                                    <!-- THÊM datalist -->
                                    <input type="text" name="name" list="patientList" required>
                                    <datalist id="patientList"></datalist>
                                </td>
                                <td> <label>Mã bệnh nhân:</label>
                                    <input type="text" name="patientId"></td>
                            </tr>
                            <tr>
                                <td> <label>Ngày khám:</label>
                                    <input type="date" name="visitDate"></td>
                                <td>  <label>Chẩn đoán:</label>
                                    <select name="diagnosis">
                                        <option value="Viêm nha chu">Viêm nha chu</option>
                                        <option value="Sâu răng">Sâu răng</option>
                                        <option value="Viêm lợi">Viêm lợi</option>
                                        <option value="Khác">Khác</option>
                                    </select></td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div>
                    <h3>Danh Sách Thuốc</h3>
                    <table id="medicineTable" border="1">
                        <tr>
                            <th>Tên thuốc</th>
                            <th>Liều dùng</th>
                            <th>Số lần/ngày</th>
                            <th>Số ngày</th>
                            <th>Ghi chú</th>
                            <th>Thao tác</th>
                        </tr>
                        <tr>
                            <td><input type="text" name="medName1"></td>
                            <td><input type="text" name="dose1"></td>
                            <td><input type="text" name="freq1"></td>
                            <td><input type="text" name="days1"></td>
                            <td><input type="text" name="note1"></td>
                            <td><button type="button" class="remove-btn" onclick="removeRow(this)">-</button></td>
                        </tr>
                    </table>
                    <button type="button" class="add-btn" onclick="addMedicineRow()">+</button>
                </div>

                <div>
                    <h3>Lưu ý dùng thuốc:</h3>
                    <textarea name="instructions" rows="4" placeholder="..."></textarea>
                </div>

                <div>
                    <h3>Thông Tin Bác Sĩ</h3>
                    <label>Bác sĩ kê đơn:</label>
                    <!-- THÊM datalist -->
                    <input type="text" name="doctorName" list="doctorList">
                    <datalist id="doctorList"></datalist>
                </div>

                <input class="btn-submit" type="submit" value="Lưu Toa Thuốc">
            </form>
        </div>

        <!-- Thêm jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

        <!-- Script autocomplete + thêm/xóa thuốc -->
        <script>
            let rowCount = 1;

            function addMedicineRow() {
                rowCount++;
                const table = document.getElementById("medicineTable");
                const newRow = table.insertRow(-1);

                const fields = ["medName", "dose", "freq", "days", "note"];
                for (let i = 0; i < fields.length; i++) {
                    const cell = newRow.insertCell(i);
                    const input = document.createElement("input");
                    input.name = fields[i] + rowCount;
                    cell.appendChild(input);
                }

                // Ô thao tác: nút xóa
                const actionCell = newRow.insertCell(fields.length);
                const removeBtn = document.createElement("button");
                removeBtn.type = "button";
                removeBtn.className = "remove-btn";
                removeBtn.innerText = "-";
                removeBtn.onclick = function() { removeRow(this); };
                actionCell.appendChild(removeBtn);
            }

            function removeRow(button) {
                const row = button.parentNode.parentNode;
                const table = document.getElementById("medicineTable");
                if (table.rows.length <= 2) {
                    alert("Phải có ít nhất một thuốc trong toa thuốc!");
                } else {
                    table.deleteRow(row.rowIndex);
                }
            }

            // === Autocomplete ===
            function setupAutoComplete(inputSelector, roleId) {
                $(inputSelector).on("input", function() {
                    let keyword = $(this).val();
                    if (keyword.length >= 2) {
                        $.getJSON("${pageContext.request.contextPath}/searchUser",
                            { keyword: keyword, roleId: roleId },
                            function(data) {
                                let datalistId = $(inputSelector).attr("list");
                                $("#" + datalistId).empty();
                                data.forEach(item => {
                                    $("#" + datalistId).append(`<option value="${item.fullName}">`);
                                });
                            });
                    }
                });
            }

            $(document).ready(function() {
                setupAutoComplete("input[name='name']", 1); // Bệnh nhân
                setupAutoComplete("input[name='doctorName']", 3); // Bác sĩ
            });
        </script>
    </body>
</html>
