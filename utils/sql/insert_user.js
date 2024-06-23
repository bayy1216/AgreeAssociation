import { fakerKO as faker } from "@faker-js/faker";
import mysql from 'mysql2';

// MySQL 연결 설정
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'agree'
});

// users 테이블 데이터 생성 및 삽입
const insertUsers = () => {
    let users = [];
    for (let i = 0; i < 10_0000; i++) {
        const nickname = faker.person.firstName();
        const profileImage = faker.image.avatar();
        const email = faker.internet.email()+i.toString();
        const password = '$2a$10$gJemDnKe1aiEGn2KGBg4X.3EpTCLJZX6boKLk46e0fbDrtaZU06bG';
        const point = faker.number.int({ min: 0, max: 1000 });
        const role = 'USER';
        const createdAt = faker.date.past();
        const modifiedAt = faker.date.recent();
        users.push([createdAt, modifiedAt, nickname, profileImage, email, password, point, role]);
    }

    const sql = 'INSERT INTO users (created_at, modified_at, nickname, profile_image_url, email, password, point, role) VALUES ?';
    connection.query(sql, [users], (err, results) => {
        if (err) throw err;
        console.log('Inserted ' + results.affectedRows + ' rows into users table.');
    });
};


// 각 테이블에 데이터 삽입
connection.connect((err) => {
    if (err) throw err;
    console.log('Connected to MySQL database.');

    // 데이터 삽입 함수 호출
    insertUsers();
    // 다른 테이블 데이터 삽입 함수들도 추가할 수 있음

    // 연결 종료
    connection.end();
});
