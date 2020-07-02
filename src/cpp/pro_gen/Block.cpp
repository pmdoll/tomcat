/**
 * @file Block.cpp
 * @brief This file implements the methods in the Block class.
 */
#include "Block.h"
#include <iostream>
#include <string>

using namespace std;

/**
 * @brief Construct a new Block:: Block object
 *
 * @param blockName The semantic name for this block which is not the same as
 * its material
 * @param blockMaterial The material the block is made of
 * @param blockPos The position of the block in the Minecraft world
 */
Block::Block(string blockName, string blockMaterial, Pos* blockPos)
    : name(blockName), material(blockMaterial), pos(*blockPos) {}

/**
 * @brief Get the name of the block
 *
 * @return string The block's name
 */
string Block::getName() { return this->name; }

/**
 * @brief Get the material of the block
 *
 * @return string The block's material
 */
string Block::getMaterial() { return this->material; }

/**
 * @brief Get the X coordinate of the block
 *
 * @return int The x coordinate
 */
int Block::getX() { return this->pos.getX(); }

/**
 * @brief Get the Y coordinate of the block
 *
 * @return int The y coordinate
 */
int Block::getY() { return this->pos.getY(); }

/**
 * @brief Get the Z coordinate of the block
 *
 * @return int The z coordinate
 */
int Block::getZ() { return this->pos.getZ(); }

/**
 * @brief Gets a string representation of the various
 * fields and values stores in an instance
 *
 * @return string The string representation
 */
string Block::toString() {
    string retval = "Name: " + this->name + "\n" +
                    "Material: " + this->material + "\n" +
                    "Pos: " + (this->pos).toString();
    return retval;
}

/**
 * @brief Destroy the Block:: Block object
 */
Block::~Block() {}