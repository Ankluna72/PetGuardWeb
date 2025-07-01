<%
    String msg = request.getParameter("msg");
    if ("eliminado".equals(msg)) {
%>
    <script>alert("? ¡Tu cuenta fue eliminada correctamente!");</script>
<%
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PETGUARD - Login</title>
    <link rel="stylesheet" href="css/estilos.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
    <script>
        // Mostrar mensajes de error si existen
        window.onload = function() {
            generateCaptcha();
            
            // Manejar parámetros de error en la URL
            const urlParams = new URLSearchParams(window.location.search);
            const error = urlParams.get('error');
            
            if(error) {
                let message = '';
                switch(error) {
                    case '1': message = 'DNI o contraseña incorrectos'; break;
                    case '2': message = 'Captcha incorrecto'; break;
                    case '3': message = 'Sesión expirada'; break;
                    default: message = 'Error al iniciar sesión';
                }
                alert('? ' + message);
            }
        };

        function generateCaptcha() {
            const chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            let captcha = "";
            for (let i = 0; i < 6; i++) {
                captcha += chars.charAt(Math.floor(Math.random() * chars.length));
            }
            document.getElementById('captchaText').textContent = captcha;
            document.getElementById('captchaGenerated').value = captcha;
        }
    </script>
</head>
<body>
    <div class="background-container"></div>
    
    <div class="login-container">
        <img src="img/logoL.png" alt="PETGUARD Logo" class="logo">
        
        <form action="validarCaptcha.jsp" method="post" onsubmit="return validateForm()">
            <h2>INICIAR SESIÓN</h2>

            <div class="input-group">
                <label for="dni">DNI</label>
                <input type="text" id="dni" name="dni" placeholder="Ingrese su DNI" required>
            </div>

            <div class="separator"></div>

            <div class="input-group">
                <label for="clave">CONTRASEÑA</label>
                <input type="password" id="clave" name="clave" placeholder="***********" required>
            </div>

            <div class="captcha-container">
                <div class="captcha-code" id="captchaText"></div>

                <div class="captcha-input-container">
                    <input type="text" id="captchaInput" name="captchaInput" class="captcha-input" placeholder="Código" required>
                    <span class="refresh-captcha" onclick="generateCaptcha()">
                        <svg viewBox="0 0 24 24">
                            <path d="M17.65,6.35C16.2,4.9 14.21,4 12,4A8,8 0 0,0 4,12A8,8 0 0,0 12,20C15.73,20 18.84,17.45 19.73,14H17.65C16.83,16.33 14.61,18 12,18A6,6 0 0,1 6,12A6,6 0 0,1 12,6C13.66,6 15.14,6.69 16.22,7.78L13,11H20V4L17.65,6.35Z"/>
                        </svg>
                    </span>
                </div>

                <input type="hidden" name="captchaGenerated" id="captchaGenerated">
            </div>

            <input type="submit" value="ENTRAR">
            
            
            <div class="register-link">
    ¿No tienes cuenta? <a href="javascript:void(0)" onclick="showRegistroPopup()">Regístrate</a>
</div>

    <!-- POPUP REGISTRO -->
    <div id="registroPopup" style="display:none; position:fixed; top:0; left:0; right:0; bottom:0; background:rgba(0,0,0,0.4); z-index:999; align-items:center; justify-content:center;">
        <div style="background:#fff; padding:24px 18px 18px 18px; border-radius:8px; min-width:250px; box-shadow:0 6px 40px rgba(0,0,0,0.18); position:relative; text-align:center;">
            <span style="position:absolute;top:10px;right:16px;cursor:pointer;font-size:1.8em;color:#888;" onclick="closeRegistroPopup()">&times;</span>
            <h2 style="font-size:1.2em;margin-bottom:18px;font-weight:600;color:#3498db;">Registrarse como:</h2>
            <div style="display:flex; gap:24px; justify-content:center; margin:10px 0;">
                <a href="/PetGuardWeb/Vista/VistaPropietario/agregar.jsp?accion=add" style="display:flex;flex-direction:column;align-items:center;text-decoration:none;color:#2c3e50;padding:6px;">
                    <img src="img/Propietario.png" alt="Propietario" style="width:64px;height:64px;margin-bottom:6px;border-radius:50%;background:#e1eefd;padding:7px;">
                    <span>Propietario</span>
                </a>
                <a href="/PetGuardWeb/Vista/VistaVeterinario/agregar.jsp?accion=add" style="display:flex;flex-direction:column;align-items:center;text-decoration:none;color:#2c3e50;padding:6px;">
                    <img src="img/Veterinario.png" alt="Veterinario" style="width:64px;height:64px;margin-bottom:6px;border-radius:50%;background:#e1eefd;padding:7px;">
                    <span>Veterinario</span>
                </a>
            </div>
        </div>
    </div>

            
        </form>
    </div>

    <script>
        function validateForm() {
            // Validación adicional puede ir aquí
            return true;
        }
    </script>
    
    
    <script>
    function showRegistroPopup() {
        document.getElementById('registroPopup').style.display = 'flex';
    }
    function closeRegistroPopup() {
        document.getElementById('registroPopup').style.display = 'none';
    }
    </script>
    
    
</body>
</html>