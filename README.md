# 🧠 WellNest - Mental Wellness Application

> A comprehensive mental health and wellness platform designed to help users improve their emotional well-being through interactive games, journaling, meditation, and personalized questionnaires.

[![Java](https://img.shields.io/badge/Java-25.0.2-orange?style=flat-square&logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.6.3-green?style=flat-square&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-5.0-green?style=flat-square&logo=mongodb)](https://www.mongodb.com/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)](LICENSE)
[![Status](https://img.shields.io/badge/Status-Active-brightgreen?style=flat-square)]()

---

## ✨ Features

### 🎮 Interactive Games
- **Wordle Challenge** - Test your vocabulary with classic word-guessing gameplay
- **Breathe Box** - Guided breathing exercises for stress relief and relaxation
- **Meditation Timer** - Customizable meditation sessions to track your mindfulness journey

### 📝 Personal Journaling
- Create, read, update, and delete personal notes
- Organize thoughts and track emotional patterns
- Secure note storage with user authentication

### 📋 Wellness Questionnaire
- Self-assessment tools to evaluate emotional well-being
- Personalized insights based on questionnaire responses
- Track progress over time with detailed results

### 👤 User Profile Management
- Secure user registration and authentication
- Profile customization
- Track activity history and wellness metrics
- Password-protected accounts

### 🎨 Modern User Interface
- Responsive design for desktop and mobile devices
- Intuitive navigation and smooth user experience
- Beautiful gradient UI with modern styling
- Dark mode support

---

## 🏗️ Technical Architecture

### Backend Stack
- **Framework**: Spring Boot 2.6.3
- **Language**: Java 25.0.2
- **Security**: Spring Security with role-based access control (RBAC)
- **Database**: MongoDB Atlas (Cloud)
- **Build Tool**: Maven with wrapper

### Frontend Stack
- **Template Engine**: Thymeleaf 3.0.14
- **CSS Framework**: Bootstrap 5.0.2
- **Styling**: Custom CSS with gradients and animations
- **Icons**: Font Awesome 6.0.0
- **JavaScript**: Vanilla JS with DOM manipulation

### Database Schema
- **Users Collection**: User credentials, profile data, roles
- **Notes Collection**: Personal journal entries with timestamps
- **Questionnaire Responses**: Self-assessment data with insights
- **Game State**: Wordle attempts, meditation session data

---

## 🚀 Quick Start

### Prerequisites
- Java 25.0.2 or higher
- Maven 3.8.4+
- MongoDB Atlas account (or local MongoDB)
- Git

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/parikshithsivakumar/MentalWellness.git
cd SBT-Enotes
```

2. **Set up environment variables**
```bash
# For Windows (PowerShell)
$env:JAVA_HOME="C:\Program Files\Eclipse Adoptium\jdk-25.0.2.10-hotspot"

# Update application.properties with your MongoDB connection string
```

3. **Configure MongoDB**
Edit `src/main/resources/application.properties`:
```properties
spring.data.mongodb.uri=mongodb+srv://username:password@cluster.mongodb.net/wellnest
```

4. **Build the application**
```bash
./mvnw clean package -DskipTests
```

5. **Run the application**
```bash
java -jar target/SBT-Enotes-0.0.1-SNAPSHOT.jar
```

6. **Access the application**
```
Open your browser and navigate to: http://localhost:8080
```

---

## 📁 Project Structure

```
SBT-Enotes/
├── src/
│   ├── main/
│   │   ├── java/com/enotes/
│   │   │   ├── controller/        # REST controllers & routing
│   │   │   ├── service/           # Business logic (Wordle, etc.)
│   │   │   ├── entity/            # Database models
│   │   │   ├── repository/        # MongoDB data access
│   │   │   ├── config/            # Spring configuration
│   │   │   ├── web/               # Web utilities (WebResult, etc.)
│   │   │   └── webservices/       # Enums & utilities
│   │   ├── resources/
│   │   │   ├── templates/         # Thymeleaf HTML templates
│   │   │   │   ├── user/          # User-specific pages
│   │   │   │   ├── login.html
│   │   │   │   └── signup.html
│   │   │   ├── static/
│   │   │   │   ├── css/           # Stylesheets
│   │   │   │   ├── js/            # JavaScript files
│   │   │   │   └── img/           # Images & assets
│   │   │   ├── words.txt          # Wordle dictionary (5-letter words)
│   │   │   └── application.properties
│   │   └── SbtEnotesApplication.java
│   └── test/
│       └── java/com/enotes/
└── pom.xml

```

---

## 🎮 Game Features

### Wordle Challenge
- **Dictionary**: 5,000+ five-letter words from `words.txt`
- **Feedback System**: 
  - 🟩 Green tile = Letter in correct position
  - 🟨 Yellow tile = Letter in word, wrong position
  - ⬜ Gray tile = Letter not in word
- **Session Persistence**: Game state saved per user session
- **Real-time Validation**: Client & server-side validation

### Meditation Timer
- **Customizable Duration**: Users can set meditation length
- **Visual Feedback**: Progress bar and countdown timer
- **Breathing Guide**: Calm UI optimized for focus
- **Session History**: Track meditation sessions

### Breathe Box
- **Guided Breathing**: 4-7-8 breathing pattern
- **Stress Relief**: Calming colors and animations
- **Customizable Pace**: Adjust breathing rhythm to preference
- **Relaxation Tracking**: Monitor breathing sessions

---

## 🔐 Security Features

- **Password Encryption**: BCrypt password hashing
- **Spring Security**: Role-based access control
- **Session Management**: HttpSession for user state
- **CSRF Protection**: Built-in Spring Security CSRF tokens
- **Secure Headers**: HTTP security headers configured
- **User Authorization**: Endpoint-level access control

---

## 📊 Database Models

### User Entity
```
- userId: ObjectId (Primary Key)
- email: String (Unique, Required)
- name: String (Required)
- password: String (Encrypted, Required)
- role: String (ROLE_USER, ROLE_ADMIN)
- enabled: Boolean
- createdAt: LocalDateTime
```

### Notes Entity
```
- noteId: ObjectId (Primary Key)
- userId: ObjectId (Foreign Key)
- title: String
- content: String
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
```

### Questionnaire Response
```
- responseId: ObjectId (Primary Key)
- userId: ObjectId (Foreign Key)
- responses: Map<String, Integer>
- score: Integer
- timestamp: LocalDateTime
```

---

## 🖥️ Key Pages

| Page | Route | Purpose |
|------|-------|---------|
| Login | `/login` | User authentication |
| Signup | `/signup` | New user registration |
| Dashboard | `/user/main` | Home page after login |
| Wordle Game | `/user/wordle` | Interactive word game |
| Meditation Timer | `/user/timer` | Meditation sessions |
| Breathe Box | `/user/breathbox` | Guided breathing |
| Notes | `/user/viewNotes` | Journal management |
| Questionnaire | `/user/questionnaire` | Mental wellness assessment |
| User Profile | `/user/viewProfile` | Profile management |

---

## 🔧 Configuration

### Application Properties
```properties
# MongoDB Configuration
spring.data.mongodb.uri=mongodb+srv://username:password@cluster.mongodb.net/wellnest

# Server Configuration
server.port=8080
server.servlet.context-path=/

# Thymeleaf Configuration
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/

# Security Configuration
spring.security.user.name=admin
spring.security.user.password=admin123
```

---

## 📦 Dependencies

Core Dependencies:
- Spring Boot Web
- Spring Data MongoDB
- Spring Security
- Thymeleaf
- Lombok
- Jakarta Servlet API

Development Dependencies:
- Maven Compiler Plugin
- JUnit 5 (Testing)

---

## 🚢 Deployment

### Building for Production
```bash
./mvnw clean package -DskipTests -P production
```

### Docker Support (Optional)
```dockerfile
FROM openjdk:25
COPY target/SBT-Enotes-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**Parikshith Sivakumar**
- GitHub: [@parikshithsivakumar](https://github.com/parikshithsivakumar)
- Email: parikshith@example.com

---

## 🙏 Acknowledgments

- Spring Boot team for the amazing framework
- MongoDB for reliable cloud database
- Bootstrap for responsive UI components
- Font Awesome for beautiful icons
- All contributors and supporters

---

## 📞 Support

For support, email support@wellnest.com or open an issue on GitHub.

---

## 🎯 Roadmap

- [ ] Multi-language support
- [ ] Push notifications for reminders
- [ ] Social sharing features
- [ ] Advanced analytics dashboard
- [ ] AI-powered emotional insights
- [ ] Integration with wearable devices
- [ ] Mobile app (iOS/Android)
- [ ] Community features

---

<div align="center">

Made with ❤️ for mental wellness

⭐ If you find this project helpful, please star it!

</div>
