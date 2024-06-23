import { fakerKO as faker } from "@faker-js/faker";
import mysql from 'mysql2';

// MySQL 연결 설정
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'agree'
});


// article 테이블 데이터 생성 및 삽입 (예시)
const insertArticles = () => {
    let articles = [];
    for (let i = 0; i < 9990; i++) { // 예시로 100만건
        const title = faker.lorem.sentence();
        const content = faker.lorem.paragraph();
        const viewsCount = faker.number.int({ min: 0, max: 5000 });
        const createdAt = faker.date.past();
        const modifiedAt = faker.date.recent();
        const statusType = ['HONORED', 'PENDING'];
        const articleStatus = statusType[Math.floor(Math.random() * statusType.length)];
        const authorId = faker.number.int({ min: 1, max: 99999 }); // assuming 100000 users


        articles.push([createdAt, modifiedAt, articleStatus, content, title, viewsCount, authorId]);
    }

    const sql = 'INSERT INTO article (created_at, modified_at, article_status, content, title, views_count, author_id) VALUES ?';
    connection.query(sql, [articles], (err, results) => {
        if (err) throw err;
        console.log('Inserted ' + results.affectedRows + ' rows into article table.');
    });
};




// 각 테이블에 데이터 삽입
connection.connect((err) => {
    if (err) throw err;
    console.log('Connected to MySQL database.');

    // 데이터 삽입 함수 호출
    for (let i = 0; i < 1; i++) {
        insertArticles();
    }

    // 다른 테이블 데이터 삽입 함수들도 추가할 수 있음

    // 연결 종료
    connection.end();
});
