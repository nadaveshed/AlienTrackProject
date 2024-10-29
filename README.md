# Alien Management System

Hello and welcome to the House of Fun! This project is developed to help save the world from an alien invasion by documenting all captured aliens. The system allows staff to insert and view dataset entries for different types of aliens.

## Project Overview

In this application, we have three types of aliens:

1. **Alien Warrior**
   - **Fields**:
     - ID
     - Name
     - Commander ID
     - Weapon (Options: Water gun / Pepper spray / Chopsticks)

2. **Alien Commander**
   - **Fields**:
     - ID
     - Name
     - Commander ID
     - Vehicle (Options: Bird scooter / Merkava tank)
   - **Note**: Can directly manage up to 10 alien warriors.

3. **Alien Chief-Commander**
   - **Fields**:
     - ID
     - Name
     - Vehicle (Options: Bird scooter / Merkava tank / Egged Bus)
   - **Note**: Can directly manage up to 3 alien commanders.

### API Endpoints

The server application exposes the following API routes:

- `POST /api/aliens/newAlien` - Adds a new captured alien to the dataset.
- `GET /api/aliens/getAll` - Returns all aliens from the dataset (fields: id, name, weapon, vehicle, commander ID, and commander name).

## Technology Stack

- **Backend**: Java with Spring Boot
- **Frontend**: JavaScript/TypeScript with React.js

## How to Run the Project

To run the project, follow these steps:

### Backend (Spring Boot)

1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/alien-management-system.git
   cd alien-management-system

2. **Navigate to the Backend Directory The Spring Boot files are located in the main folder.**

Run the Spring Boot Application

Ensure you have Java Development Kit (JDK) installed (version 11 or higher).
Use Maven to build and run the application:
bash
Copy code
./mvnw spring-boot:run
The server will start on http://localhost:8080.
Frontend (React)
Navigate to the Frontend Directory The React application files are in the main folder.

Install Dependencies

Make sure you have Node.js and npm installed.
bash
Copy code
npm install
Run the React Application

bash
Copy code
npm start
The React app will open in your default web browser at http://localhost:3000.
Multi-Tab Functionality
The application is designed to handle changes across multiple tabs. Any updates made in one tab will be reflected in all open tabs in real-time.
