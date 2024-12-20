import React from 'react';
import { Outlet } from 'react-router-dom';
import { useDependencies } from './hooks';
import { Alert, Space } from 'antd';

export const Main = () => {
	const { notification, onCloseMessage } = useDependencies();
	return (
		<div>
			<h1>Home</h1>

			<Space direction='vertical' style={{ width: '100%' }}>
				{notification?.message != null ? (
					<Alert
						closable
						description={notification?.message}
						type={notification?.type}
						showIcon
						onClose={onCloseMessage}
					/>
				) : null}
			</Space>
			<Outlet />
		</div>
	);
};