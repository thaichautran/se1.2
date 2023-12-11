# se1.2
Tìm hiểu OpenAPI và Swagger, kết hợp ReactJS hoặc VueJS
### Hosting web: https://memorise-d34b0.web.app/

### Technologies
- Frontend: Vuejs
- Backend: Spring boot
- Database: Mysql
### Cài đặt bên phía backend: MySQL, MySQLWorkbench, Intelij, Postman in FE local, and test local
#### 1.Cài đặt MySQL lên Docker:
#####  1.1 Cài đặt Docker Desktop: 
  - Download từ trang web và cài đặt: **https://docs.docker.com/desktop/install/windows-install/**
  - Download WSL từ trang web vài cài đặt(kéo xuống step 4): **https://learn.microsoft.com/en-us/windows/wsl/install-manual**
  - Sau khi cài đặt xong, ta sẽ có được biểu tượng Docker Desktop:
#####  1.2 Cài đặt MySQL trên Docker:
  - Mở Docker Desktop vừa tải về
  - Mở cửa sổ cmd, gõ lệnh: **docker pull mysql**
  - Sau khi pull về, mở tab images trong Docker Desktop kiểm tra xem images mysql đã được pull về chưa
    ![markdown](https://drive.google.com/file/d/1n285Ljri1zGfI7-hK8mnlbUpG5LP43FP/view?usp=drive_link)
  - gõ lệnh để chạy images mysql:
    **docker run --name name(tự đặt) -p 3307(port ngoài, tự đặt):3306(port trong) -e MYSQL_ROOT_PASSWORD=password(tự đặt) -d mysql:latest**
  - Sau khi run, vào tab container, kiểm tra xem MySQL đã được run hay chưa, nếu thấy container MySQL đã chạy ở trạng thái running là thành công
    ![markdown](https://drive.google.com/file/d/1m6NmJofpuUV_7BBDvpHq5aquAwEIIYgC/view?usp=drive_link)
#### 2.Cài đặt MySQL MySQLWorkbench:
#####  2.1 Download MySQLWorkbench từ trang web và cài đặt: **https://dev.mysql.com/downloads/workbench/**
#####  2.2 Mở MySQLWorkbench lên để kết nối với MySQL trên Docker: 
  - Click vào nút dấu + để tạo kết nối mới, một bảng config sẽ hiện ra
   ![markdown](https://drive.google.com/file/d/1IM8zbj1ho21q1RqGA5AwERlnS3Fsxl-A/view?usp=drive_link)
  - Tại dòng Connection Name: Nhập tên cho MySQL Connection
  - Tại dòng Port điền port ngoài đã cài đặt cho container MySQL đã config trước đó, ở đây là 3307
  - Tại dòng Store in Vault: nhập password đã setup trước đó, ở đây là admin123
  - Port và Password bạn có thể setup tùy theo ý bạn
  - Sau đó ấn vào Test connection, nếu thành công, một pop-up sẽ hiện ra:
    ![markdown](https://drive.google.com/file/d/1oG-OgjL1hl4Jy_eVPDYEyV2M0ebcXKD6/view?usp=drive_link)
  - Ấn ok, khi đó một MySQL Connection đã được tạo, click vào đó để làm việc với MySQL
  - Pull code dự án từ github về thì mở file SQL Script trong dự án tại: memorise_backend/src/main/java/sql_command/memorise_sql.sql, và ấn vào biểu tượng Run(hình sấm sét trên thanh công cụ) để tạo database
   ![markdown]([https://drive.google.com/file/d/1oG-OgjL1hl4Jy_eVPDYEyV2M0ebcXKD6/view?usp=drive_link](https://drive.google.com/file/d/1pKwpOGjpYgCF5ZiVQH55YWULBuaTJNao/view?usp=drive_link))
#### 3.Cài đặt Postman để testAPI:
  - Download Postman và cài đặt: **https://www.postman.com/downloads/**
#### 4.Cài đặt Intelij bản Ultimate:
  - Thực hiện theo hướng dẫn sau: **https://quoc9x.com/2022/03/01/intellij-huong-dan-cai-dat-va-kich-hoat-chi-tiet/?fbclid=IwAR37FdW0G8p6pCc29ZyY71YpIxGjMlJ59UkZq_TadmwVbCZ326_0myOpmDQ**
#### Như vậy là setup thành công các công cụ cần thiết bên phía backend, mỗi lần chạy dự án, thì start docker lên trước, sau đó vào Intelij có code backend của dự án và chạy chương trình, như vậy là server và database đã sẵn sàng để làm việc.
### Goal
Tạo ra một ứng dụng web đa chức năng, cho phép người dùng tạo, lưu trữ và chia sẻ kỷ niệm thông qua hình ảnh và video một cách dễ dàng và linh hoạt.

- **Phát triển giao diện thân thiện và dễ sử dụng:** Tạo trải nghiệm người dùng tốt nhất thông qua giao diện trực quan, tương tác linh hoạt và dễ dàng trong việc tải lên, quản lý hình ảnh và tạo kỷ niệm.

- **Tối ưu hóa hệ thống lưu trữ:** Xây dựng một cơ sở hạ tầng lưu trữ mạnh mẽ, đảm bảo an toàn và bảo mật cho dữ liệu hình ảnh cá nhân của người dùng.

- **Tích hợp tính năng tạo và tùy chỉnh kỷ niệm:** Phát triển công cụ đa dạng và linh hoạt để người dùng có thể tạo, chỉnh sửa và tùy chỉnh kỷ niệm dựa trên hình ảnh.

- **Xây dựng tính năng chia sẻ kỷ niệm:** Tạo cơ chế chia sẻ linh hoạt, cho phép người dùng chia sẻ kỷ niệm với bạn bè, gia đình hoặc cộng đồng một cách đơn giản và an toàn.

- **Xây dựng tính năng khôi phục ảnh sau khi xóa:** Xây dựng hệ thống khôi phục dữ liệu an toàn và hiệu quả, cung cấp tính năng phục hồi dữ liệu đơn giản và nhanh chóng cho người dùng khi cần thiết.

- **Đảm bảo tính ổn định và hiệu suất:** Kiểm tra và tối ưu hóa hiệu suất hệ thống để đảm bảo khả năng truy cập nhanh chóng và ổn định cho người dùng trong mọi tình huống.

- **Phát triển cộng đồng người dùng** :Xây dựng cộng đồng sử dụng tích cực, tạo điều kiện cho việc chia sẻ và tương tác thông tin giữa các người dùng thông qua nền tnagr này

- **Đảm bảo bảo mật thông tin cá nhân:** Tuân thủ nghiêm ngặt các tiêu chuẩn bảo mật dữ liệu và thông tin cá nhân của người dùng, đảm bảo sự riêng tư của mỗi người dùng.

### Business Objective
**Hiệu suất tải ảnh lên:**
Đối với người dùng có tốc độ mạng trên 10 Mbps, cần đảm bảo tốc độ tải ảnh và video lên như sau:
- **Ảnh có dung lượng thấp** (<500 KB) cần đảm bảo thời gian tải lên hệ thống không quá 3 giây.
- **Ảnh có dung lượng cao **(> 5MB) cần đảm bảo thời gian tải lên hệ thống không quá 7 giây
- Với **video có độ phân giải dưới 720p và độ dài 1 phút**, cần đảm bảo thời gian tải lên dưới 1 phút
- Với **video có độ phân giải 4K và độ dài 1 phút**, cần đảm bảo thời gian tải lên dưới 5 phút.

**Hiệu suát lưu trữ:**
- Dung lượng lưu trữ miễn phí của mỗi người dùng là 5GB và có thể mở rộng thêm
- Dung lượng lưu trữ của toàn bộ hệ thống là 100TB

**Chia sẻ và tương tác với kỷ niệm:**
- Đạt 85% người dùng đánh giá cao về trải nghiệm sử dụng và tính thân thiện của giao diện tạo kỷ niệm.
- Đạt tỷ lệ 90% người dùng tin tưởng và sử dụng tính năng chia sẻ kỷ niệm.

**Khôi phục ảnh sau khi xóa:**
- Đảm bảo 100% người dùng có thể khôi phục dữ liệu ảnh đã bị xóa trong vòng 30 ngày kể từ ngày xóa.

### Phân loại mức độ nghiêm trọng của lỗi:
1. Cao:
- **Mô tả: **Những lỗi này ảnh hưởng nghiêm trọng tới toàn bộ hệ thống, khiến hệ thống không thể sử dụng được. Những lỗi này có thể gây mất dữ liệu, làm hệ thống bị treo, gây ra các lỗi nghiêm trọng về bảo mật.
- Ví dụ: Hệ thống không xác thực được người dùng
2. Trung bình:
- **Mô tả:** Những lỗi này ảnh hưởng đến những chức năng quan trọng trong hệ thống nhưng những phần còn lại của hệ thống không bị ảnh hưởng. Những lỗi này có thể gây ra những vấn đề về hiệu suất hoặc tạo ra sự khó chịu với người dùng.
- **Ví dụ:** Khi người dùng nhập thông tin vào phần điền thông tin không đúng yêu cầu của hệ thống, người dùng không nhận được thông báo lỗi trả về.
3. Thấp:
- **Mô tả:** Những lỗi này gần như không ảnh hưởng đến hệ thống. Những lỗi này có thể tạo ra những lỗi liên quan đến mặt hiển thị nhưng không ảnh hưởng đến các chức năng chính.
- **Ví dụ:** Ảnh hiển thị không đúng với tỷ lệ mô tả ban đầu.




