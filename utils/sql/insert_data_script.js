import { fakerKO as faker } from "@faker-js/faker";
const mysql = require('mysql');

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
    for (let i = 0; i < 10000; i++) {
        const nickname = faker.internet.userName();
        const profileImage = faker.image.avatar();
        const email = faker.internet.email();
        const password = faker.internet.password();
        const point = faker.random.number(1000);
        const role = faker.random.arrayElement(['ADMIN', 'USER']);
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

// article 테이블 데이터 생성 및 삽입 (예시)
const insertArticles = () => {
    let articles = [];
    for (let i = 0; i < 1000000; i++) { // 예시로 100만건
        const title = faker.lorem.sentence();
        const content = faker.lorem.paragraph();
        const viewsCount = faker.random.number(1000);
        const createdAt = faker.date.past();
        const modifiedAt = faker.date.recent();
        const articleStatus = faker.random.arrayElement(['HONORED', 'PENDING']);
        const authorId = faker.random.number({ min: 1, max: 10000 }); // assuming 10000 users

        articles.push([createdAt, modifiedAt, articleStatus, content, title, viewsCount, authorId]);
    }

    const sql = 'INSERT INTO article (created_at, modified_at, article_status, content, title, views_count, author_id) VALUES ?';
    connection.query(sql, [articles], (err, results) => {
        if (err) throw err;
        console.log('Inserted ' + results.affectedRows + ' rows into article table.');
    });
};

// article_agree 테이블 데이터 생성 및 삽입
const insertArticleAgree = () => {
    let articleAgrees = [];
    for (let i = 0; i < 2000000; i++) { // 예시로 200만건
        const articleId = faker.random.number({ min: 1, max: 1000000 }); // 예시: article 테이블의 ID 범위에 맞게 설정
        const userId = faker.random.number({ min: 1, max: 10000 }); // 예시: users 테이블의 ID 범위에 맞게 설정
        articleAgrees.push([articleId, userId]);
    }

    const sql = 'INSERT INTO article_agree (article_id, user_id) VALUES ?';
    connection.query(sql, [articleAgrees], (err, results) => {
        if (err) throw err;
        console.log('Inserted ' + results.affectedRows + ' rows into article_agree table.');
    });
};

// article_comment 테이블 데이터 생성 및 삽입
const insertArticleComment = () => {
    let articleComments = [];
    for (let i = 0; i < 5000000; i++) { // 예시로 500만건
        const articleId = faker.random.number({ min: 1, max: 1000000 }); // 예시: article 테이블의 ID 범위에 맞게 설정
        const authorId = faker.random.number({ min: 1, max: 10000 }); // 예시: users 테이블의 ID 범위에 맞게 설정
        const createdAt = faker.date.past();
        const modifiedAt = faker.date.recent();
        const content = faker.lorem.paragraph();
        const parentPath = ''; // 예시로 빈 문자열

        articleComments.push([createdAt, modifiedAt, content, parentPath, articleId, authorId]);
    }

    const sql = 'INSERT INTO article_comment (created_at, modified_at, content, parent_path, article_id, author_id) VALUES ?';
    connection.query(sql, [articleComments], (err, results) => {
        if (err) throw err;
        console.log('Inserted ' + results.affectedRows + ' rows into article_comment table.');
    });
};

// article_disagree 테이블 데이터 생성 및 삽입
const insertArticleDisagree = () => {
    let articleDisagrees = [];
    for (let i = 0; i < 50000; i++) { // 예시로 50000개의 데이터 삽입
        const articleId = faker.random.number({ min: 1, max: 1000000 }); // 예시: article 테이블의 ID 범위에 맞게 설정
        const userId = faker.random.number({ min: 1, max: 10000 }); // 예시: users 테이블의 ID 범위에 맞게 설정
        articleDisagrees.push([articleId, userId]);
    }

    const sql = 'INSERT INTO article_disagree (article_id, user_id) VALUES ?';
    connection.query(sql, [articleDisagrees], (err, results) => {
        if (err) throw err;
        console.log('Inserted ' + results.affectedRows + ' rows into article_disagree table.');
    });
};

// 각 테이블에 데이터 삽입
connection.connect((err) => {
    if (err) throw err;
    console.log('Connected to MySQL database.');

    // 데이터 삽입 함수 호출
    insertUsers();
    insertArticles();
    // 다른 테이블 데이터 삽입 함수들도 추가할 수 있음

    // 연결 종료
    connection.end();
});

// 각 테이블에 데이터 삽입
connection.connect((err) => {
    if (err) throw err;
    console.log('Connected to MySQL database.');

    // 데이터 삽입 함수 호출
    insertArticleAgree();
    insertArticleComment();
    insertArticleDisagree();
    // 다른 테이블 데이터 삽입 함수들도 추가할 수 있음

    // 연결 종료
    connection.end();
});