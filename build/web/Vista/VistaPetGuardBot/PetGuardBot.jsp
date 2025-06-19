<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>PetGuard ChatBot</title>
    <meta charset="UTF-8">
    <style>
        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
            /* Fondo con imagen, cubre toda la pantalla y se ajusta */
            background: url('../../img/fondobot2.jpg') no-repeat center center fixed;
            background-size: cover;
        }
        .iframe-container {
            width: 100vw;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .responsive-iframe {
            width: 98vw;
            height: 96vh;
            border: none;
            border-radius: 20px;
            box-shadow: 0 4px 32px #b6c3d6;
            background: #fff;
            transition: box-shadow 0.25s, border-radius 0.25s;
        }
        @media (max-width: 700px) {
            .responsive-iframe {
                width: 100vw;
                height: 100vh;
                border-radius: 0;
                box-shadow: none;
            }
        }
        /* Botón flotante superior derecho */
        .floating-btn {
            position: fixed;
            top: 28px;
            right: 34px;
            z-index: 9999;
            background: #ffffffcc;
            color: #384966;
            border: none;
            border-radius: 50px;
            padding: 13px 22px 13px 18px;
            font-size: 1rem;
            font-weight: bold;
            box-shadow: 0 2px 10px #b6c3d6;
            cursor: pointer;
            display: flex;
            align-items: center;
            gap: 8px;
            transition: background 0.2s, color 0.2s, box-shadow 0.2s;
        }
        .floating-btn:hover {
            background: #384966;
            color: #fff;
        }
        .floating-btn .home-icon {
            width: 20px;
            height: 20px;
            display: inline-block;
            vertical-align: middle;
            margin-right: 3px;
            fill: currentColor;
        }
        @media (max-width: 700px) {
            .floating-btn {
                top: 12px;
                right: 10px;
                padding: 9px 16px 9px 13px;
                font-size: 0.95rem;
            }
        }
    </style>
</head>
<body>
    <!-- Botón flotante para volver a principal.jsp -->
    <button class="floating-btn" onclick="window.location.href='../../principal.jsp'">
        <svg class="home-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
            <path d="M3 11.5V22h6v-6h6v6h6V11.5l-9-8-9 8z"/>
        </svg>
        Principal
    </button>
    <div class="iframe-container">
        <iframe
            class="responsive-iframe"
            src="https://www.chatbase.co/chatbot-iframe/FIfBDSTDC1P8Mm_m2r7EZ"
            allow="clipboard-write"
            title="PetGuard ChatBot"
        ></iframe>
    </div>
</body>
</html>