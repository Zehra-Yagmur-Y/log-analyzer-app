# log-analyzer-app
Log kayıtlarını analiz eden web uygulaması

 Log Analyzer - Log Analiz Uygulaması

Log Analyzer, sistem loglarını analiz etmek, filtrelemek ve kullanıcıya görsel olarak sunmak için geliştirilmiş bir web tabanlı log izleme platformudur.

---

 Özellikler (Features)

-  Logları seviyelerine göre gruplama (**INFO, DEBUG, ERROR**)
-  Tarih bazlı filtreleme ve metin arama
-  Log özetlerini görsel tablo halinde sunma
-  Bootstrap tabanlı responsive arayüz
-  Genişletilebilir yapı: e-posta bildirimi, AI destekli hata yanıtı (gelecek özellik)

---

 Kurulum ve Çalıştırma (Setup & Run)

 Backend (Spring Boot)

**Gereksinimler:**
- Java 17+
- Maven

**Adımlar:**
```bash
# Proje dizinine gir
cd log-analyzer-backend

# Maven ile uygulamayı başlat
mvn spring-boot:run
```

API endpoint'leri:
- `GET http://localhost:8080/api/logs` → Tüm logları getirir
- `GET http://localhost:8080/api/logs?level=ERROR&date=2025-03-15` → Filtrelenmiş loglar
- `GET http://localhost:8080/api/logs/summary` → Log seviyelerine göre özet istatistikler

###  Frontend (React + Bootstrap)

**Gereksinimler:**
- Node.js
- npm

**Adımlar:**
```bash
# Proje dizinine gir
cd log-analyzer-frontend

# Paketleri yükle
npm install

# Uygulamayı başlat
npm start
```

Frontend: `http://localhost:3000` üzerinde çalışır.

---

##  Klasör Yapısı (Folder Structure)

```
log-analyzer-app/
├       # Spring Boot Backend
│   ├── src/main/java/com/...    # Java servisleri, controller
│   └── logs/test.log            # Log verileri (örnek)
├── log-analyzer-frontend/       # React Frontend
│   ├── src/components/LogList.js
│   └── public/
└── README.md
```

---

---

##  Gelecek Özellikler (Upcoming Features)

-  E-Posta ile hata bildirimi (SMTP)
-  DeepSeek Gemini entegrasyonu ile AI destekli hata açıklaması
-  Log seviyelerine göre grafik gösterimi

---

##  Geliştirici Notu (Dev Note)

Bu proje bireysel olarak geliştirilmiştir. Ekip içi katkılar detaylandırıldığında proje detayları güncellenecektir. Kodların alıntılanması durumunda lütfen referans gösteriniz. 

---

**Made with by Zehra Yağmur Y**


