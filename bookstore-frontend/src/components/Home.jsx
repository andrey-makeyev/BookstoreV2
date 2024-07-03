import React from 'react';
import { Container, Typography } from '@mui/material';
import BookTable from './BookTable';
import Navbar from './Navbar';

const Home = () => {
    return (
        <div>
            <Navbar />
            <Container>
                <Typography variant="h4" gutterBottom>
                    Our Books
                </Typography>
                <BookTable />
            </Container>
        </div>
    );
};

export default Home;