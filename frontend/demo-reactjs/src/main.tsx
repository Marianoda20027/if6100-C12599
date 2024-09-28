import React, { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import RegisterUser from './pages/RegisterUser';
createRoot(document.getElementById('root')!).render(
	<StrictMode>
		<RegisterUser />
	</StrictMode>,
);
