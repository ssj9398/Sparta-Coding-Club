package com.sparta.springcore;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/api/products/*", loadOnStartup = 1)
public class AllInOneServlet extends HttpServlet {
    // 신규 관심상품 등록
    // POST /api/products
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 요청 Body 의 JSON -> 자바 객체
        ProductRequestDto requestDto = null;
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);

            ObjectMapper objectMapper = new ObjectMapper();
            requestDto = objectMapper.readValue(jb.toString(), ProductRequestDto.class);
        } catch (Exception e) { /*report an error*/ }

        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Product product = new Product(requestDto);
        LocalDateTime now = LocalDateTime.now();
        product.setCreatedAt(now);
        product.setModifiedAt(now);

        // DB 연결
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:springcoredb", "sa", "");
            // DB Query 작성
            PreparedStatement ps = connection.prepareStatement("select max(id) as id from product");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // product id 설정 = product 테이블의 마지막 id + 1
                product.setId(rs.getLong("id") + 1);
            } else {
                throw new SQLException("product 테이블의 마지막 id 값을 찾아오지 못했습니다.");
            }

            ps = connection.prepareStatement("insert into product(id, title, image, link, lprice, myprice, created_at, modified_at) values(?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setLong(1, product.getId());
            ps.setString(2, product.getTitle());
            ps.setString(3, product.getImage());
            ps.setString(4, product.getLink());
            ps.setInt(5, product.getLprice());
            ps.setInt(6, product.getMyprice());
            ps.setString(7, product.getCreatedAt().toString());
            ps.setString(8, product.getModifiedAt().toString());
            // DB Query 실행
            ps.executeUpdate();
            // DB 연결 해제
            ps.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // 자바 객체 -> JSON 으로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String productJson = objectMapper.writeValueAsString(product);

        // 응답 보내기
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(productJson);
        out.flush();
    }

    // 희망 최저가 변경
    // PUT /api/products/{id}
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 요청 URL 에 입력되어 있는 'id' 파라미터 조회
        String uri = request.getRequestURI();
        String idStr = uri.substring(uri.lastIndexOf("/") + 1);

        Long id = Long.parseLong(idStr);

        // 요청 BODY 의 JSON -> 자바 객체
        ProductMypriceRequestDto requestDto = null;

        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);

            ObjectMapper objectMapper = new ObjectMapper();
            requestDto = objectMapper.readValue(jb.toString(), ProductMypriceRequestDto.class);
        } catch (Exception e) { /*report an error*/ }

        Product product = new Product();

        try {
            // DB 연결
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:springcoredb", "sa", "");

            // DB Query 작성
            PreparedStatement ps = connection.prepareStatement("select * from product where id = ?");
            ps.setLong(1, id);
            // DB Query 실행
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                product.setId(rs.getLong("id"));
                product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                product.setModifiedAt(rs.getTimestamp("modified_at").toLocalDateTime());
                product.setImage(rs.getString("image"));
                product.setLink(rs.getString("link"));
                product.setLprice(rs.getInt("lprice"));
                product.setMyprice(rs.getInt("myprice"));
                product.setTitle(rs.getString("title"));
            } else {
                throw new NullPointerException("해당 아이디가 존재하지 않습니다.");
            }

            // DB Query 작성
            ps = connection.prepareStatement("update product set myprice = ?, modified_at = ? where id = ?");
            ps.setInt(1, requestDto.getMyprice());
            ps.setString(2, LocalDateTime.now().toString());
            ps.setLong(3, product.getId());
            // DB Query 실행
            ps.executeUpdate();
            // DB 연결 해제
            rs.close();
            ps.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // 자바 객체 -> JSON 으로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String productIdJson = objectMapper.writeValueAsString(product.getId());

        // 응답 보내기 (업데이트된 상품 id)
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(productIdJson);
        out.flush();
    }

    // 등록된 전체 상품 목록 조회
    // GET /api/products
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArrayList<Product> products = new ArrayList<>();

        try {
            // DB 연결
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:springcoredb", "sa", "");
            // DB Query 작성 및 실행
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from product");
            // DB Query 결과를 상품 객체 리스트로 변환
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                product.setModifiedAt(rs.getTimestamp("modified_at").toLocalDateTime());
                product.setImage(rs.getString("image"));
                product.setLink(rs.getString("link"));
                product.setLprice(rs.getInt("lprice"));
                product.setMyprice(rs.getInt("myprice"));
                product.setTitle(rs.getString("title"));
                products.add(product);
            }
            // DB 연결 해제
            rs.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // 자바 객체 -> JSON 으로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String productsJson = objectMapper.writeValueAsString(products);

        // 응답 보내기
        response.setHeader("Content-Type", "application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(productsJson);
        out.flush();
    }
}