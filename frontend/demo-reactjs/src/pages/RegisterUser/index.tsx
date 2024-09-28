import React from "react";
import { Input, Space, Button } from "antd"; // Import Button if needed

const RegisterUser: React.FC = () => {
    return (
        <Space direction="vertical" style={{ width: '100%' }}>
            <Input placeholder="Nombre" />
            <Input placeholder="Correo" />
            <Input.Password placeholder="Contraseña" /> 
            <Button type="primary">Registrar</Button> 
        </Space>
    );
};

export default RegisterUser;
