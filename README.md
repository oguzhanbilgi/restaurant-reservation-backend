# Restaurant Reservation System - Backend

Spring Boot tabanlı restoran rezervasyon sistemi backend uygulaması.

## 🚀 Özellikler

- RESTful API tasarımı
- JWT tabanlı kimlik doğrulama
- Rol tabanlı yetkilendirme (USER, ADMIN)
- MySQL veritabanı entegrasyonu
- Rezervasyon yönetimi
- Kullanıcı yönetimi
- Menü yönetimi

## 🛠️ Kullanılan Teknolojiler

- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- MySQL
- JWT
- Lombok
- Maven

## 📦 Kurulum

1. Repoyu klonlayın:
```bash
git clone https://github.com/oguzhanbilgi/restaurant-reservation-backend.git
```

2. MySQL veritabanı oluşturun:
```sql
CREATE DATABASE restaurant_reservation;
```

3. `application.properties` dosyasını düzenleyin:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/restaurant_reservation
spring.datasource.username=your_username
spring.datasource.password=your_password
```

4. Projeyi derleyin:
```bash
mvn clean install
```

5. Uygulamayı başlatın:
```bash
mvn spring-boot:run
```

## 🌐 API Endpoints

### Kimlik Doğrulama
- `POST /api/auth/register` - Yeni kullanıcı kaydı
- `POST /api/auth/login` - Kullanıcı girişi
- `POST /api/auth/refresh` - Token yenileme

### Rezervasyonlar
- `POST /api/reservations` - Yeni rezervasyon oluşturma
- `GET /api/reservations` - Rezervasyonları listeleme
- `GET /api/reservations/{id}` - Rezervasyon detayı
- `PUT /api/reservations/{id}` - Rezervasyon güncelleme
- `DELETE /api/reservations/{id}` - Rezervasyon silme

### Kullanıcılar
- `GET /api/users/profile` - Kullanıcı profili
- `PUT /api/users/profile` - Profil güncelleme
- `PUT /api/users/change-password` - Şifre değiştirme

### Admin
- `GET /api/admin/users` - Tüm kullanıcıları listeleme
- `GET /api/admin/reservations` - Tüm rezervasyonları listeleme
- `PUT /api/admin/users/{id}/role` - Kullanıcı rolü güncelleme

## 🔒 Güvenlik

- JWT tabanlı kimlik doğrulama
- Şifre hashleme (BCrypt)
- Rol tabanlı yetkilendirme
- CORS yapılandırması
- Rate limiting

## 📝 Yapılacaklar

- [ ] E-posta doğrulama
- [ ] Şifremi unuttum fonksiyonu
- [ ] Redis cache entegrasyonu
- [ ] Swagger API dokümantasyonu
- [ ] Birim testleri
- [ ] Docker desteği

## 🤝 Katkıda Bulunma

1. Bu repoyu fork edin
2. Yeni bir branch oluşturun (`git checkout -b feature/amazing-feature`)
3. Değişikliklerinizi commit edin (`git commit -m 'Add some amazing feature'`)
4. Branch'inizi push edin (`git push origin feature/amazing-feature`)
5. Pull Request oluşturun

## 📄 Lisans

Bu proje MIT lisansı altında lisanslanmıştır. Daha fazla bilgi için `LICENSE` dosyasına bakın.
