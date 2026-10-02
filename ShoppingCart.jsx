import React, { useState } from "react";
import {
  Container,
  Grid,
  Card,
  CardMedia,
  CardContent,
  Typography,
  Button,
  IconButton,
  Badge,
  Drawer,
  List,
  ListItem,
  ListItemText,
  Divider,
  Box,
} from "@mui/material";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import AddIcon from "@mui/icons-material/Add";
import RemoveIcon from "@mui/icons-material/Remove";
import DeleteIcon from "@mui/icons-material/Delete";

// Sample product data — replace with API data or props
const products = [
  { id: 1, name: "Wireless Headphones", price: 1499, image: "https://picsum.photos/seed/1/300/200" },
  { id: 2, name: "Smart Watch", price: 2999, image: "https://picsum.photos/seed/2/300/200" },
  { id: 3, name: "Bluetooth Speaker", price: 1299, image: "https://picsum.photos/seed/3/300/200" },
  { id: 4, name: "Laptop Backpack", price: 999, image: "https://picsum.photos/seed/4/300/200" },
  { id: 5, name: "Mechanical Keyboard", price: 2499, image: "https://picsum.photos/seed/5/300/200" },
  { id: 6, name: "USB-C Hub", price: 799, image: "https://picsum.photos/seed/6/300/200" },
];

export default function ShoppingCart() {
  const [cart, setCart] = useState([]);
  const [drawerOpen, setDrawerOpen] = useState(false);

  const addToCart = (product) => {
    setCart((prev) => {
      const existing = prev.find((item) => item.id === product.id);
      if (existing) {
        return prev.map((item) =>
          item.id === product.id ? { ...item, qty: item.qty + 1 } : item
        );
      }
      return [...prev, { ...product, qty: 1 }];
    });
  };

  const updateQty = (id, delta) => {
    setCart((prev) =>
      prev
        .map((item) =>
          item.id === id ? { ...item, qty: item.qty + delta } : item
        )
        .filter((item) => item.qty > 0)
    );
  };

  const removeFromCart = (id) => {
    setCart((prev) => prev.filter((item) => item.id !== id));
  };

  const totalItems = cart.reduce((sum, item) => sum + item.qty, 0);
  const totalPrice = cart.reduce((sum, item) => sum + item.qty * item.price, 0);

  return (
    <Container sx={{ py: 4 }}>
      {/* Header */}
      <Box
        sx={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          mb: 4,
        }}
      >
        <Typography variant="h4" fontWeight={600}>
          Shop
        </Typography>
        <IconButton onClick={() => setDrawerOpen(true)}>
          <Badge badgeContent={totalItems} color="primary">
            <ShoppingCartIcon fontSize="large" />
          </Badge>
        </IconButton>
      </Box>

      {/* Product grid */}
      <Grid container spacing={3}>
        {products.map((product) => (
          <Grid item xs={12} sm={6} md={4} key={product.id}>
            <Card>
              <CardMedia
                component="img"
                height="180"
                image={product.image}
                alt={product.name}
              />
              <CardContent>
                <Typography variant="h6">{product.name}</Typography>
                <Typography variant="body1" color="text.secondary" sx={{ mb: 2 }}>
                  ₹{product.price}
                </Typography>
                <Button
                  variant="contained"
                  fullWidth
                  onClick={() => addToCart(product)}
                >
                  Add to Cart
                </Button>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>

      {/* Cart drawer */}
      <Drawer anchor="right" open={drawerOpen} onClose={() => setDrawerOpen(false)}>
        <Box sx={{ width: 340, p: 2 }}>
          <Typography variant="h5" sx={{ mb: 2 }}>
            Your Cart
          </Typography>
          {cart.length === 0 ? (
            <Typography color="text.secondary">Cart is empty.</Typography>
          ) : (
            <>
              <List>
                {cart.map((item) => (
                  <ListItem key={item.id} divider>
                    <ListItemText
                      primary={item.name}
                      secondary={`₹${item.price} x ${item.qty} = ₹${item.price * item.qty}`}
                    />
                    <IconButton size="small" onClick={() => updateQty(item.id, -1)}>
                      <RemoveIcon fontSize="small" />
                    </IconButton>
                    <IconButton size="small" onClick={() => updateQty(item.id, 1)}>
                      <AddIcon fontSize="small" />
                    </IconButton>
                    <IconButton size="small" onClick={() => removeFromCart(item.id)}>
                      <DeleteIcon fontSize="small" />
                    </IconButton>
                  </ListItem>
                ))}
              </List>
              <Divider sx={{ my: 2 }} />
              <Typography variant="h6">Total: ₹{totalPrice}</Typography>
              <Button variant="contained" color="success" fullWidth sx={{ mt: 2 }}>
                Checkout
              </Button>
            </>
          )}
        </Box>
      </Drawer>
    </Container>
  );
}
