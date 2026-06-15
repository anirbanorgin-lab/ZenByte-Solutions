-- ZenByte Solutions Database Schema

-- Create Database
CREATE DATABASE IF NOT EXISTS zenbyte_db;
USE zenbyte_db;

-- Contacts Table
CREATE TABLE IF NOT EXISTS contacts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    service VARCHAR(100),
    message LONGTEXT,
    status VARCHAR(20) DEFAULT 'NEW',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Quote Requests Table
CREATE TABLE IF NOT EXISTS quote_requests (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    company_name VARCHAR(255) NOT NULL,
    contact_name VARCHAR(100) NOT NULL,
    contact_email VARCHAR(100) NOT NULL,
    project_type VARCHAR(100) NOT NULL,
    budget VARCHAR(50) NOT NULL,
    project_description LONGTEXT,
    status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_email (contact_email),
    INDEX idx_status (status),
    INDEX idx_project_type (project_type),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Job Applications Table
CREATE TABLE IF NOT EXISTS job_applications (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    position VARCHAR(100) NOT NULL,
    years_of_experience INT NOT NULL,
    cover_letter LONGTEXT,
    status VARCHAR(20) DEFAULT 'SUBMITTED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_position (position),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Projects Table (Optional for Portfolio)
CREATE TABLE IF NOT EXISTS projects (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description LONGTEXT,
    technologies VARCHAR(500),
    image_url VARCHAR(500),
    case_study LONGTEXT,
    status VARCHAR(50) DEFAULT 'COMPLETED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Team Members Table (Optional)
CREATE TABLE IF NOT EXISTS team_members (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    position VARCHAR(100) NOT NULL,
    bio LONGTEXT,
    image_url VARCHAR(500),
    email VARCHAR(100),
    linkedin_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Index for better query performance
CREATE INDEX idx_contacts_email_created ON contacts(email, created_at);
CREATE INDEX idx_quotes_email_created ON quote_requests(contact_email, created_at);
CREATE INDEX idx_applications_position_created ON job_applications(position, created_at);
