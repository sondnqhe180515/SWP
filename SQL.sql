USE dental_clinic;
GO

DROP TABLE IF EXISTS bookings;
GO

CREATE TABLE bookings (
    id INT PRIMARY KEY IDENTITY(1,1),
    booking_for NVARCHAR(20),
    relative_name NVARCHAR(100),
    fullname NVARCHAR(100) NOT NULL,
    phone NVARCHAR(20) NOT NULL,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    service NVARCHAR(100) NOT NULL
);
